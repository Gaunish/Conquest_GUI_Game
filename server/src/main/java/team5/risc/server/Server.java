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
  ServerSock server_sock;
  int total_units;

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
    this.server_sock = new ServerSock();
  }

  //Test constructor
  public Server(int listen_port, int no) throws SocketException, IOException, ClassNotFoundException{
    this(listen_port);
    this.run(no);
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
    Map map = new Map(3*num_player,num_player);
    Players players = new Players(num_player);

    //Accept client connection
    server_sock.AcceptConnection(num_player, serverSocket, clientSocketSet);
    System.out.println("All players are connected, let's start the game!");

    // initial units assigned by server
    total_units = 50;

    //Add client streams to the list
    server_sock.init(clientSocketSet);

    /*
    ---------------------------------------------------------------------
      PLACEMENT PHASE
    ---------------------------------------------------------------------
    */

    PlacementPhase placement = new PlacementPhase();
    placement.DoPlacement(num_player, server_sock, map, total_units);
    System.out.println("Placement Phase has finished");

    /*
     * ACTION PHASE
     *  ------------------------------------------------ 
     * Protocol:
     * ------------------------------------------------ 
     * Server sends a string explaining
     * which action it is going to recieve ->
     * 
     * 1) Move for MoveAction 2) Attack for AttackAction 3) Done for DoneAction 
     * 4) Upgrade for UpgradeAction
     * -------------------------------------------------
     * 
     * ------------------------------------------------ 
     * Protocol 2
     * ------------------------------------------------ 
     * Server will tell client his
     * status - 1) Winner 2) Loser 3) Player
     * ------------------------------------------------
     * 
     * ------------------------------------------------ 
     * Protocol 3
     * ------------------------------------------------ 
     * At beginning of turn, server
     * will tell client if there is any winner - 1) No Winner OR 2) Player i has won
     */

      ActionPhase play_game = new ActionPhase();
      play_game.doAction(map, num_player, server_sock, players);
      System.out.println("Game has ended, Thanks for Playing!");

    /*
    ---------------------------------------------------------
    EXIT PHASE
    ---------------------------------------------------------
    */
    server_sock.close();

    //Close all clients socket
    for (Socket c : clientSocketSet) {
     c.close();
    }

    //Closer server socket
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
    Server fs = new Server(Integer.parseInt(args[0]));
    fs.run(Integer.parseInt(args[1]));
  }
}
