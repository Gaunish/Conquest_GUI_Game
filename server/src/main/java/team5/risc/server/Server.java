package team5.risc.server;

import team5.risc.common.*;
// import team5.risc.common.Player;
import java.net.*;
import java.io.*;

import java.util.ArrayList;

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
    Map map = new Map();
    for (int i = 0; i < num_player; ++i) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("client " + i + " accepted");
      System.out.println(clientSocket);
      clientSocketSet.add(clientSocket);
    }
    System.out.println("All client finished, begin to read data");

    int id = 0;
    // initial units assigned by server
    int total_units = 5;

    // class to send strings to clients
    MetaInfo strInfo = new MetaInfo();
    strInfo.unitStr(total_units);

    // list of region of areas assigned to each player
    ArrayList<Region> regions = map.getInitRegions();

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

      // ask for input for each player
      for (String area : txt_region) {
        System.out.println("id : " + id + " Area: " + area);
        strInfo.placeStr(area);
        dataOutputStream.writeUTF(strInfo.place_unit);
        dataOutputStream.flush();

        int no = -1;
        try {
          no = (int) dataInputStream.readInt();
          AreaNode node = map.getAreaNodeByName(area);
          System.out.println("no:" + no);
          region.set_init_unit(node, no);
        } catch (Exception e) {
          System.out.println(e);
        }
        System.out.println("Recieved " + no + " for " + area + " by Player " + id);
      }

      id++;
    }

    /*
     * ACTION PHASE
     * ------------------------------------------------
     * Protocol:
     * ------------------------------------------------
     * Server a string explaining
     * which action it is going to recieve ->
     * 
     * 1) Move for MoveAction
     * 2) Attack for AttackAction
     * 3) Done for DoneAction
     * ------------------------------------------------
     */

    /// Validation and execute Move Action
    ActionValidator actionValidator = new ActionValidator();
    ActionExecutor actionExecutor = new ActionExecutor();
    for (int index = 0; index < num_player; index++) {
      ObjectInputStream objIstream = objectInputStreamList.get(index);
      ObjectOutputStream objOstream = objectOutputStreamList.get(index);
      DataInputStream dataItream = dataInputStreamList.get(index);
      DataOutputStream dataOtream = dataOutputStreamList.get(index);

      while (true) {
        // Receive Actions
        System.out.println("Try to fetch action from player " + index);
        
        //Get the action type
        String action = dataItream.readUTF();
        
        //MOVE, DONE ACTION
        if(action.equals("Move") || action.equals("Done")){
          MoveAction moveAction = (MoveAction) objIstream.readObject();
          
          //DONE ACTION
          if (moveAction.is_terminated) {
            System.out.println("Player " + index + " finished, go to the next player");
            dataOtream.writeUTF("correct and done");
            break;
          }

          //MOVE ACTION
          System.out.println("Move action from Player " + moveAction.player_id);
          String res = actionValidator.isValid(moveAction, map);
          if (res != null) {
            dataOtream.writeUTF(res);
          } else {
            dataOtream.writeUTF("correct");
            actionExecutor.execute(moveAction, map);
          }
        }
        //ATTACK ACTION
        else if(action.equals("Attack")){
          AttackAction attackAction = (AttackAction) objIstream.readObject();
          System.out.println("Attack action from Player " + attackAction.player_id);
          String res = actionValidator.isValid(attackAction, map);
          if (res != null) {
            dataOtream.writeUTF(res);
          } else {
            dataOtream.writeUTF("correct");
            actionExecutor.execute(attackAction, map);
          }
        }
      }
    }

    /// Take action (execute Attack Action, then combat)

    for (DataInputStream stream : dataInputStreamList) {
      stream.close();
    }
    for (DataOutputStream stream : dataOutputStreamList) {
      stream.close();
    }
    for (ObjectInputStream stream : objectInputStreamList) {
      stream.close();
    }
    for (ObjectOutputStream stream : objectOutputStreamList) {
      stream.close();
    }

    for (Socket c : clientSocketSet) {
      c.close();
    }

    serverSocket.close();
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
    Server fs = new Server(1651);
    fs.run(1);
  }
}
