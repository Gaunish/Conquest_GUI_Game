package team5.risc.server;

import team5.risc.common.Thing;
import java.net.*;
import java.io.*;

// public class Server {
//   private ServerSocket serverSocket;
//   private Socket clientSocket;
//   PrintWriter out;
//   int listen_port;
//   public int factorial(int x) {
//     int ans = 1;
//     while (x > 0) {
//       ans = ans * x;
//       x --;
//     }
//     return ans;
//   }

//   public void startServer() {
//     try{
//       serverSocket = new ServerSocket(listen_port);
//       while (true) {
//         clientSocket = serverSocket.accept();
//         out = new PrintWriter(clientSocket.getOutputStream(), true);
//         out.println("Hello this is server");
//         clientSocket.close();
//       }
//     } catch (IOException e){
//       System.out.println("IO failed"+e.getMessage());
//       System.exit(1);
//     }

//     // clientSocket = serverSocket.accept();
//     //     out = new PrintWriter(clientSocket.getOutputStream(), true);
//     //     in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//     //     String greeting = in.readLine();
//     //         if ("hello server".equals(greeting)) {
//     //             out.println("hello client");
//     //         }
//     //         else {
//     //             out.println("unrecognised greeting");
//     //         }
//   }

//   public Server(int port) {
//     listen_port = port;
//   }
//   public static void main(String[] args) {
//     Thing t = new Thing("server");
//     Server server = new Server(1651);
//     server.startServer();
//     System.out.println(t);
//   }
// }


import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
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
  ServerSocket sock;
  ThreadPoolExecutor threadPool;
  Factorer factorer;
  /**
   * This constructts a FactorServer with the specified
   * Factorer and ServerSocket.
   * @param factorer The factorer to use to factor numbers that are read over
   * the socket.
   * @param sock The server socket to listen on
   * @throws SocketException if thrown by attempting to set the listen
   * timeout of the passed in ServerSocket.
   */
  public Server(Factorer factorer, ServerSocket sock)
      throws SocketException {
    this.sock = sock;
    this.factorer = factorer;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(32);
    threadPool = new ThreadPoolExecutor(2, 16, 5, TimeUnit.SECONDS, workQueue);
    sock.setSoTimeout(1000);
  }
  /**
   * This is a helper method to accept a socket from the ServerSocket
   * or return null if it timesout.
   */
  private Socket acceptOrNull() {
    try {
      return sock.accept();
    } catch (IOException ioe) {
      // In real code, we would want to be more discriminating here.
      // Was this a timeout, or some other problem?
      return null;
    }
  }
  /**
   * This method is the main loop of the FactorServer.
   * It accepts requests, and then queues them for
   * work in a thread pool.  Note that this method
   * runs until the current thread is interrupted
   * (by some other thread).
   */
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      final Socket s = acceptOrNull();
      if (s == null) {
        continue;
      }
      // This will enqueue the request until
      // a thread in the pool is available, then
      // execute that request on the available thread.
      threadPool.execute(new Runnable() {
        @Override
        public void run() {
          try {
            try {
              FactorIO fio =
                  new FactorIO(new NumberInputStreamReader(s.getInputStream()),
                               new PrintWriter(s.getOutputStream()), factorer);
              fio.handleRequest();
            } finally {
              s.close();
            }
          } catch (IOException ioe) {
            // in something real, we would want to handle
            // this better... but for this, there isn't much we can or
            // really want to do.
          }
        }
      });
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
    Server fs =
        new Server(new PrimeFactors(), new ServerSocket(1651));
    fs.run();
  }
}













