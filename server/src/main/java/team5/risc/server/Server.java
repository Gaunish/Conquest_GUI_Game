package team5.risc.server;

import team5.risc.common.Thing;
import java.net.*;
import java.io.*;

public class Server {
  private ServerSocket serverSocket;
  private Socket clientSocket;
  PrintWriter out;
  int listen_port;
  public int factorial(int x) {
    int ans = 1;
    while (x > 0) {
      ans = ans * x;
      x --;
    }
    return ans;
  }

  public void startServer() {
    try{
      serverSocket = new ServerSocket(listen_port);
      while (true) {
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("Hello this is server");
        clientSocket.close();
      }
    } catch (IOException e){
      System.out.println("IO failed");
    }

    
    // clientSocket = serverSocket.accept();
    //     out = new PrintWriter(clientSocket.getOutputStream(), true);
    //     in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //     String greeting = in.readLine();
    //         if ("hello server".equals(greeting)) {
    //             out.println("hello client");
    //         }
    //         else {
    //             out.println("unrecognised greeting");
    //         }
  }

  public Server(int port) {
    listen_port = port;
  }
  public static void main(String[] args) {
    Thing t = new Thing("server");
    Server server = new Server(1651);
    server.startServer();
    System.out.println(t);
  }
}













