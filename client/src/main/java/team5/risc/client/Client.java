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

import javax.annotation.meta.When;
import javax.sound.midi.SysexMessage;

import java.util.concurrent.ThreadPoolExecutor;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Client {

  // MoveAction getActionFromSTDIN() {

  // }

  public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
    Scanner in = new Scanner(System.in);
    Integer port = Integer.parseInt(args[1]);

    Socket client = new Socket(args[0], port);
    System.out.println(client.getRemoteSocketAddress());

    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
    ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());

    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
    DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
    
    int id = (int) dataInputStream.readInt();
    System.out.println("id :" + id + "\n");

    String no_assigned = dataInputStream.readUTF();

    //int size = (int) dataStream.readInt();
    ArrayList<String> regions = (ArrayList<String>) objectInputStream.readObject();
    System.out.print(no_assigned);
    System.out.println("You have been assigned region : " + regions);
   
    //read input
    Input user_in = new TextInput(System.in, System.out);
    String name = "Player " + id;

    /*
    ----------------------------------------------------------------------------
      PLACEMENT PHASE
    ----------------------------------------------------------------------------
    */
    //Send output for each area
    for(int i = 0; i < regions.size(); i++){
      String area = dataInputStream.readUTF();

      while(true){
        int no = user_in.getPlacement(area);
  
        //send int
        dataOutputStream.writeInt(no);
        dataOutputStream.flush();

        String result = dataInputStream.readUTF();
        if(result.equals("Success")){
          break;
        }
        else{
          System.out.println(result);
        }
      }
    }

    /*
    ----------------------------------------------------------------------------
      MOVE PHASE
    ----------------------------------------------------------------------------
    */

    ArrayList<MoveAction> moveActionList = new ArrayList<>();

    /*
      ------------------------------------------------
      Protocol 1:
      ------------------------------------------------
      Client first sends a string explaining 
      which action it is going to send ->
      
      1) Move for MoveAction
      2) Attack for AttackAction
      3) Done for DoneAction
      ------------------------------------------------

      ------------------------------------------------
      Protocol 2
      ------------------------------------------------
      Server will tell client his status -
      1) Winner
      2) Loser
      3) Player
      ------------------------------------------------
    */

    while(true){
      //get map
      String map_str = dataInputStream.readUTF();
      System.out.println(map_str);

      //get winner status of game
      String game_status = dataInputStream.readUTF();
      System.out.println(game_status);
      if(!game_status.equals("No winner")){
        //String winner_str = dataInputStream.readUTF();
        System.out.println(game_status);
        break;
      }

      //get status of player from server
      String pl_status = dataInputStream.readUTF();

      if(pl_status.equals("Loser")){
        //Loser found
        //Skip taking actions
        continue;
      }

      //Each turn
      while(true){
        //Get input from player via terminal
        String action = user_in.getAction(name);

        //check if input (M)ove
        if(action.equals("M")){

          //write "Move"
          dataOutputStream.writeUTF("Move");

          MoveAction m = user_in.getMove(id);
          System.out.println("Recieved move "+m.source+" "+m.destination);
          objectOutputStream.writeObject(m);
          String response = dataInputStream.readUTF();
          System.out.println(response);
        } 

        // check if input (A)ttack
        else if(action.equals("A")){
          // write "Attack"
          dataOutputStream.writeUTF("Attack");

          AttackAction m = user_in.getAttack(id);
          System.out.println("Recieved attack " + m.source + " " + m.destination);
          objectOutputStream.writeObject(m);
          String response = dataInputStream.readUTF();
          System.out.println(response);

        }
        //Check if input (D)one
        else if (action.equals("D")){
          // write "Done"
          dataOutputStream.writeUTF("Done");
          break;
        }
      }
    }
    
    //Finished Phase
    // while(true) {

    // }
    objectInputStream.close();
    objectOutputStream.close();
    dataInputStream.close();
    dataOutputStream.close();
    client.close();
  

    in.close();
  }
}
