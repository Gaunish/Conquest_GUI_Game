package team5.risc.server;

import team5.risc.common.*;
// import team5.risc.common.Player;
import java.net.*;
import java.io.*;
import java.nio.*;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.net.SocketException;

/**
 * This class does the actual network service of
 * the factor server.   An instance of this
 * class holds a ServerSocket and a Factorer
 * and the run method listens on that socket
 * accepts requests, and sends them to
 * the given Factorer for factoring.
 *
 */
public class Server {
  ServerSocket serverSocket;
  // ThreadPoolExecutor threadPool;
  // Factorer factorer;
  HashSet<Socket> clientSocketSet;
  // private Selector selector;
  // ServerSocketChannel serverSocketChannel;
  int listen_port;

  /**
   * This constructts a FactorServer with the specified
   * Factorer and ServerSocket.
   * @param factorer The factorer to use to factor numbers that are read over
   * the socket.
   * @param sock The server socket to listen on
   * @throws SocketException if thrown by attempting to set the listen
   * timeout of the passed in ServerSocket.
   */
  public Server(int listen_port)
      throws SocketException, IOException {
    this.listen_port = listen_port;
    this.serverSocket = new ServerSocket(listen_port);
    this.clientSocketSet = new HashSet<>();
  }

  /**
   * This method is the main loop of the FactorServer.
   * It accepts requests, and then queues them for
   * work in a thread pool.  Note that this method
   * runs until the current thread is interrupted
   * (by some other thread).
   */
  public void run(int num_player) throws IOException{
    //Accept phase
    Map map = new Map(2 * num_player, num_player);
    for (int i = 0; i < num_player; ++i) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("client " + i + " accepted");
      System.out.println(clientSocket);
      clientSocketSet.add(clientSocket);
    }
    System.out.println("All client finished, begin to read data "+clientSocketSet.size());

    int id = 0;
    //initial units assigned by server
    int total_units = 5;
    
    //class to send strings to clients
    MetaInfo strInfo = new MetaInfo();
    strInfo.unitStr(total_units);
    
    //list of region of areas assigned to each player
    ArrayList<Region> regions = map.getInitRegions();
    


    for (Socket sock: clientSocketSet) {

      ObjectOutputStream outputStream = 
        new ObjectOutputStream(sock.getOutputStream());
      ObjectInputStream inputStream = 
        new ObjectInputStream(sock.getInputStream());

      DataOutputStream dataOutputStream = 
        new DataOutputStream(sock.getOutputStream());
      DataInputStream dataInputStream = 
        new DataInputStream(sock.getInputStream());

      outputStream.writeObject(map);
      outputStream.flush();

      System.out.println("Server test");
      try{
        map = (Map) inputStream.readObject();
      }
      catch(Exception e){
          System.out.println(e);
      }

      dataOutputStream.writeInt(100);
      String response_str = dataInputStream.readUTF();
      
      System.out.println("Recieved " + map.getAreasName() + " for  + area +  by Player" + id);
      System.out.println("Client->Server Map:"+response_str);
      
      id++;

      inputStream.close();
      outputStream.close(); 
    }

    for(Socket c : clientSocketSet){
      c.close();
    }
  }

  /**
   * This main method runs the factor server, listening on port 1651.
   * Specifically, it creates an instance and calls run.
   * When done from the command line, this program runs until
   * externally killed.
   * @param args is the command line arguments.  These are currently ignored.
   * @throws IOException if creation of the ServerSocket fails  (likely due
   * to the port being unavailable(.
   */
  public static void main(String[] args) throws IOException {
    Server fs = new Server(1651);
    fs.run(2);
  }
}













