/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package team5.risc.client;

import team5.risc.common.Thing;
import team5.risc.common.Map;

import java.net.*;
import java.io.*;
import java.nio.*;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.io.PrintWriter;


public class Client{
  public int sum(int n){
    int ans = 0;
    while (n > 0){
      ans = ans + n;
      n--;
    }
    return ans;
  }
  public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
    Thing t = new Thing("client");
    System.out.println(t);
    
    Socket client = new Socket("127.0.0.1", 1651);
    System.out.println(client.getRemoteSocketAddress());
    OutputStream outToServer = client.getOutputStream();
    DataOutputStream out = new DataOutputStream(outToServer);

    out.writeBytes("Hello from " + client.getLocalSocketAddress());
    InputStream is = client.getInputStream();
    ObjectInputStream inputStream = new ObjectInputStream(is);
    Map map = (Map) inputStream.readObject();
    System.out.println("LLLLL "+map.num_players);

    //close(client);
  }
}









