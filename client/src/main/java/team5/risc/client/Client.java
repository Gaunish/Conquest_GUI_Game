/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package team5.risc.client;

import team5.risc.common.*;

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
import java.util.Scanner;
import java.util.ArrayList;

public class Client {

  public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
    Socket client = new Socket("127.0.0.1", 1651);
    System.out.println(client.getRemoteSocketAddress());
    OutputStream outToServer = client.getOutputStream();
    DataOutputStream out = new DataOutputStream(outToServer);
    ObjectOutputStream out_obj = new ObjectOutputStream(outToServer);


    out.writeBytes("Hello from " + client.getLocalSocketAddress());
    out.flush();
    
    InputStream is = client.getInputStream();
    ObjectInputStream inputStream = new ObjectInputStream(is);
    DataInputStream dataStream = new DataInputStream(is);
            
    Map map = (Map) inputStream.readObject();
    /* int id = (int) dataStream.readInt();
    System.out.println("id :" + id + "\n");

    String str = dataStream.readUTF();

    //int size = (int) dataStream.readInt();
    ArrayList<String> regions = (ArrayList<String>) inputStream.readObject();
    System.out.print(str);
    System.out.println("You have been assigned region : " + regions);
    */
    //read input
    Scanner in = new Scanner(System.in);
    
    //Send output for each area
    /*  for(int i = 0; i < regions.size(); i++){
      str = dataStream.readUTF();
      System.out.print(str);

      //String no = "hi";
      /*try{
        no = in.nextInt();
      }
      catch(Exception e){
        System.out.println("Invalid input");
      }
      finally{
        if(no < 0){
          System.out.println("Invalid input");
        }
        }*/
      System.out.println("no");
      out_obj.writeObject(map);
      System.out.println("Client test");
      out_obj.flush();
      //}
    
    inputStream.close();
    dataStream.close();
    out.close();
    outToServer.close();
    is.close();
    client.close();

    //display map
    Display txt = new TextDisplay();
    txt.display(map, System.out);
    
  }
}
