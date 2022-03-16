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
    Scanner in = new Scanner(System.in);
    Socket client = new Socket("127.0.0.1", 1651);
    System.out.println(client.getRemoteSocketAddress());

    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
    ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());

    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
    DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            
    Map map = (Map) objectInputStream.readObject();
    int id = (int) dataInputStream.readInt();
    System.out.println("id :" + id + "\n");

    String str = dataInputStream.readUTF();

    //int size = (int) dataStream.readInt();
    ArrayList<String> regions = (ArrayList<String>) objectInputStream.readObject();
    System.out.print(str);
    System.out.println("You have been assigned region : " + regions);
   
    //read input
    //Scanner in = new Scanner(System.in);
    //in.useDelimiter(System.lineSeparator());
    
    //Send output for each area
    for(int i = 0; i < regions.size(); i++){
      str = dataInputStream.readUTF();

      int no = 0;
      while(true){
        try{
          System.out.print(str);
          String line = in.nextLine();
          no = Integer.parseInt(line);
          System.out.println("Line : " + no);
          break;
         }
        catch(Exception e){
          System.out.println("Invalid input - exception ");
          continue;
        }
        finally{
          if(no < 0){
            System.out.println("Invalid input");
            continue;
          }
        }
      }
      dataOutputStream.writeInt(no);
      dataOutputStream.flush();
    }
    
    objectInputStream.close();
    objectOutputStream.close();
    dataInputStream.close();
    dataOutputStream.close();
    client.close();
  
    //display map
    // System.out.print(map.getInitRegions());
    Display txt = new TextDisplay();
    txt.display(map, System.out);

    in.close();
  }
}
