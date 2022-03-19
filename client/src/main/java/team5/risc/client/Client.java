/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package team5.risc.client;
import team5.risc.common.*;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;

public class Client {
  RISCServer riscServer;
  int id;
  String name;
  ArrayList<String> regions;
  Input user_in;
  public Client(RISCServer server, Input input) throws IOException {
    riscServer = server;
    id = riscServer.readInt();
    System.out.println("id :" + id + "\n");
    name = "Player " + id;
    regions = null;
    user_in = input;
  }

  public int getID() {
    return id;
  }

  public ArrayList<String> getRegions() {
    return regions;
  }

  public ArrayList<String> getRegionPhase() throws IOException, ClassNotFoundException {
    String no_assigned = riscServer.readUTF();
    regions = (ArrayList<String>) riscServer.readObject();
    System.out.println(no_assigned);
    System.out.println("You have been assigned region : " + regions);
    return regions;
  }

  public void placementPhase() throws IOException {
    /*
    ----------------------------------------------------------------------------
      PLACEMENT PHASE
    ----------------------------------------------------------------------------
    */
    //Send output for each area
    for(int i = 0; i < regions.size(); i++){
      String area = riscServer.readUTF();
      while(true){
        int no = user_in.getPlacement(area);
        //send int
        riscServer.writeInt(no);
        String result = riscServer.readUTF();
        if(result.equals("Success")){
          System.out.println("Successly place unit " + no);
          break;
        }
        else{
          System.out.println(result);
        }
      }
    }
  }

  public void actionPhase() throws IOException {
    /*
    ----------------------------------------------------------------------------
      MOVE PHASE
    ----------------------------------------------------------------------------
    */

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
      String map_str = riscServer.readUTF();
      System.out.println(map_str);

      //get winner status of game
      String game_status = riscServer.readUTF();
      if(!game_status.equals("No winner")){
        String winner_str = riscServer.readUTF();
        System.out.println(winner_str);
        break;
      }

      //get status of player from server
      String pl_status = riscServer.readUTF();

      if(pl_status.equals("Loser")){
        continue;
      }

      //Each turn
      while(true){
        //Get input from player via terminal
        String action = user_in.getAction(name);

        //check if input (M)ove
        if(action.equals("M")){
          //write "Move"
          riscServer.writeUTF("Move");
          MoveAction m = user_in.getMove(id);
          System.out.println("Recieved move "+m.source+" "+m.destination);
          riscServer.writeObject(m);
          String response = riscServer.readUTF();
          System.out.println(response);
        } 
        // check if input (A)ttack
        else if(action.equals("A")){
          // write "Attack"
          riscServer.writeUTF("Attack");
          AttackAction m = user_in.getAttack(id);
          System.out.println("Recieved attack " + m.source + " " + m.destination);
          riscServer.writeObject(m);
          String response = riscServer.readUTF();
          System.out.println(response);
        }
        //Check if input (D)one
        else if (action.equals("D")){
          // write "Done"
          riscServer.writeUTF("Done");
          break;
        }
      }
    }
  }

  public void run() throws IOException, ClassNotFoundException {
    getRegionPhase();
    placementPhase();
    actionPhase();
    riscServer.close();
  }

  public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
    RISCServer riscServer = new RISCServer(args[0], Integer.parseInt(args[1]));
    Input user_in = new TextInput(System.in, System.out);
    Client client = new Client(riscServer, user_in);
    client.run();
  }
}
