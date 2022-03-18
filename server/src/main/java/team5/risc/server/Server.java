package team5.risc.server;

import team5.risc.common.TextDisplayMap;
import team5.risc.common.*;
import java.net.*;
import java.io.*;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ToolTipManager;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Server {
  ServerSocket serverSocket;
  ArrayList<Socket> clientSocketSet;
  int listen_port;

  /**
   * This constructts a FactorServer with the specified Factorer and ServerSocket.
   * 
   * @param factorer The factorer to use to factor numbers that are read over the
   *                 socket.
   * @param sock     The server socket to listen on
   * @throws SocketException if thrown by attempting to set the listen timeout of
   *                         the passed in ServerSocket.
   */
  public Server(int listen_port) throws SocketException, IOException {
    this.listen_port = listen_port;
    this.serverSocket = new ServerSocket(listen_port);
    this.clientSocketSet = new ArrayList<>();
  }

  /**
   * This method is the main loop of the FactorServer. It accepts requests, and
   * then queues them for work in a thread pool. Note that this method runs until
   * the current thread is interrupted (by some other thread).
   * 
   * @throws ClassNotFoundException
   */
  public void run(int num_player) throws IOException, ClassNotFoundException {
    // Accept phase
    // Map map = new Map(2 * num_player, num_player);
    Map map = new Map(4, 2);
    Players players = new Players(num_player);
    for (int i = 0; i < num_player; ++i) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("client " + i + " accepted");
      System.out.println(clientSocket);
      clientSocketSet.add(clientSocket);
    }
    System.out.println("All client finished, begin to read data");

    int id = 0;
    // initial units assigned by server
    int total_units = 50;

    // class to send strings to clients
    MetaInfo strInfo = new MetaInfo();
    strInfo.unitStr(total_units);

    // list of region of areas assigned to each player
    ArrayList<Region> regions = map.getRegions();

    ArrayList<ObjectOutputStream> objectOutputStreamList = new ArrayList<>();
    ArrayList<ObjectInputStream> objectInputStreamList = new ArrayList<>();
    ArrayList<DataOutputStream> dataOutputStreamList = new ArrayList<>();
    ArrayList<DataInputStream> dataInputStreamList = new ArrayList<>();

    for (Socket client : clientSocketSet) {
      objectOutputStreamList.add(new ObjectOutputStream(client.getOutputStream()));
      objectInputStreamList.add(new ObjectInputStream(client.getInputStream()));
      dataOutputStreamList.add(new DataOutputStream(client.getOutputStream()));
      dataInputStreamList.add(new DataInputStream(client.getInputStream()));
    }

    for (int index = 0; index < num_player; index++) {
      ObjectInputStream objectInputStream = objectInputStreamList.get(index);
      ObjectOutputStream objectOutputStream = objectOutputStreamList.get(index);
      DataInputStream dataInputStream = dataInputStreamList.get(index);
      DataOutputStream dataOutputStream = dataOutputStreamList.get(index);

      dataOutputStream.writeInt(id);
      dataOutputStream.flush();

      // send prompt
      dataOutputStream.writeUTF(strInfo.inform_unit);
      dataOutputStream.flush();

      // get region in text form for player
      Region region = regions.get(id);
      region.set_owner_id(id);
      ArrayList<String> txt_region = region.getAreasName();

      // send region in text form
      objectOutputStream.writeObject(txt_region);
      objectOutputStream.flush();

      int no_units = 0;
      // ask for input for each player
      for (String area : txt_region) {
        System.out.println("id : " + id + " Area: " + area);
        strInfo.placeStr(area);
        dataOutputStream.writeUTF(strInfo.place_unit);
        dataOutputStream.flush();

        while (true) {
          int no = -1;
          try {
            no = (int) dataInputStream.readInt();
            AreaNode node = map.getAreaNodeByName(area);
            System.out.println("no:" + no);
            System.out.println("Recieved " + no + " for " + area + " by Player " + id);
            if ((no_units + no) > total_units) {
              String error = "Placement Invalid, Input: " + no + " Remaining: " + (total_units - no_units);
              dataOutputStream.writeUTF(error);
              continue;
            } else {
              no_units += no;
              region.set_init_unit(node, no);
              dataOutputStream.writeUTF("Success");
              break;
            }
          } catch (Exception e) {
            System.out.println(e);
            continue;
          }
        }
      }

      id++;
    }

    System.out.println("Placement Phase has done");

    /*
     * ACTION PHASE ------------------------------------------------ Protocol:
     * ------------------------------------------------ Server a string explaining
     * which action it is going to recieve ->
     * 
     * 1) Move for MoveAction 2) Attack for AttackAction 3) Done for DoneAction
     * -------------------------------------------------
     * 
     * ------------------------------------------------ Protocol 2
     * ------------------------------------------------ Server will tell client his
     * status - 1) Winner 2) Loser 3) Player
     * ------------------------------------------------
     * 
     * ------------------------------------------------ Protocol 3
     * ------------------------------------------------ At beginning of turn, server
     * will tell client if there is any winner - 1) No Winner OR 2) Player i has won
     */

    /// Validation and execute Move Action
    ActionValidator actionValidator = new ActionValidator();
    ActionExecutor actionExecutor = new ActionExecutor();
    while (true) {
      // get Map info
      TextDisplayMap txt_map = new TextDisplayMap(System.out);
      String map_info = txt_map.display(map);

      // attack list init
      ArrayList<AttackAction> attackActionList = new ArrayList<>();

      for (int index = 0; index < num_player; index++) {
        ObjectInputStream objIstream = objectInputStreamList.get(index);
        ObjectOutputStream objOstream = objectOutputStreamList.get(index);
        DataInputStream dataItream = dataInputStreamList.get(index);
        DataOutputStream dataOtream = dataOutputStreamList.get(index);

        // Send Map
        dataOtream.writeUTF(map_info);

        // check if there is a winner
        int winner = players.get_winner(map, num_player);
        if (winner == -1) {
          dataOtream.writeUTF("No winner");
        } else {
          if (winner == index) {
            dataOtream.writeUTF("Congratulations! You have won.");
          } else {
            String win_str = "Player " + winner + " has won.";
            dataOtream.writeUTF(win_str);
          }
          break;
        }

        // check if player has lost
        boolean has_lost = players.has_lost(map, index);

        // send client his/her player status
        if (has_lost == true) {
          dataOtream.writeUTF("Loser");
        } else {
          dataOtream.writeUTF("Player");
        }

        while (true) {

          // Receive Actions
          System.out.println("Try to fetch action from player " + index);

          // Get the action type
          String action = dataItream.readUTF();

          // MOVE ACTION
          if (action.equals("Move")) {
            MoveAction moveAction = (MoveAction) objIstream.readObject();

            // MOVE ACTION
            System.out.println("Move action from Player " + moveAction.player_id);
            String res = actionValidator.isValid(moveAction, map);
            if (res != null) {
              dataOtream.writeUTF(res);
            } else {
              dataOtream.writeUTF("correct");
              actionExecutor.execute(moveAction, map);
            }
          }
          // ATTACK ACTION
          else if (action.equals("Attack")) {
            AttackAction attackAction = (AttackAction) objIstream.readObject();
            System.out.println("Attack action from Player " + attackAction.player_id);
            String res = actionValidator.isValid(attackAction, map);
            if (res != null) {
              dataOtream.writeUTF(res);
            } else {
              dataOtream.writeUTF("correct");
              attackActionList.add(attackAction);
            }
          } else if (action.equals("Done")) {
            break;
          }
        }
      }
      // prepare attack army
      for (AttackAction a : attackActionList) {
        System.out.println("try execute action:" + a.toString() + "\n");
        actionExecutor.execute(a, map);
      }
      System.out.println("place enemy army:\n");
      System.out.println(map.toString());
      // combat
      for (AreaNode area : map.getAreas()) {
        while (!area.noEnemyLeft()) {
          Army defender = area.getDefender();
          Army attacker = area.popFirstEnemy();
          actionExecutor.combatExecute(defender, attacker, map, area);
        }
      }

    }

    // for (DataInputStream stream : dataInputStreamList) {
    // stream.close();
    // }
    // for (DataOutputStream stream : dataOutputStreamList) {
    // stream.close();
    // }
    // for (ObjectInputStream stream : objectInputStreamList) {
    // stream.close();
    // }
    // for (ObjectOutputStream stream : objectOutputStreamList) {
    // stream.close();
    // }

    // for (Socket c : clientSocketSet) {
    // c.close();
    // }

    // serverSocket.close();
  }

  /**
   * This main method runs the factor server, listening on port 1651.
   * Specifically, it creates an instance and calls run. When done from the
   * command line, this program runs until externally killed.
   * 
   * @param args is the command line arguments. These are currently ignored.
   * @throws IOException            if creation of the ServerSocket fails (likely
   *                                due to the port being unavailable(.
   * @throws ClassNotFoundException
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Server fs = new Server(Integer.parseInt(args[0]));
    fs.run(Integer.parseInt(args[1]));
  }
}
