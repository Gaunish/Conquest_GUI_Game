package team5.risc.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import team5.risc.common.*;

public class ActionPhase {
    private DataOutputStream dataOtream;
    private Players players;
    private int num_player, turn_no;
    private Map map;
    private ActionValidator actionValidator;
    private ActionExecutor actionExecutor;
    private DataInputStream dataItream;
    private ObjectInputStream objIstream;
    private HashSet<SpyAction> spies;
    private HashSet<CloakAction> cloaks;
    private View view1, view2;

    public ActionPhase(View view1, View view2){
      this.view1 = view1;
      this.view2 = view2;
      spies = new HashSet<>();
      cloaks = new HashSet<>();
      turn_no = 0;
    }

    //init constructor
    public void setVariables(Players players, int num_player, Map map){
        this.players = players;
        this.num_player = num_player;
        this.map = map;
    }

    //method to get text version of map
    private String txt_map(){
        TextDisplayMap txt = new TextDisplayMap(System.out);
        String map_info = txt.levelDisplay(map);
        return map_info;
    }

    //method to check winner
    private boolean check_winner(int index) throws IOException{
        // check if there is a winner
        int winner = players.get_winner(map, num_player);
        if (winner == -1) {
          //No winner
          dataOtream.writeUTF("No winner");
          return false;
        } 
        else {
          //The current player is winner
          if (winner == index) {
            dataOtream.writeUTF("Congratulations! You have won.");
          } 
          //Someone else is winner
          else {
            String win_str = "Player " + winner + " has won.";
            dataOtream.writeUTF(win_str);
          }
          //Winner found
          return true;
        }
        
    }

    //method to send player status
    private boolean send_status(int index) throws IOException{
        boolean has_lost = players.has_lost(map, index);

        // send client his/her player status
        if (has_lost == true) {
          dataOtream.writeUTF("Loser");
        } else {
          dataOtream.writeUTF("Player");
        }
        return has_lost;
    }

    private void do_combat(ArrayList<AttackAction> attackActionList){
      Combat c= new DiceCombat(20, 1);
      // prepare attack army
      for (AttackAction a : attackActionList) {
        actionExecutor.execute(a, map);
      }
      //System.out.println("place enemy army:\n");
      //System.out.println(map.toString());

      //Do combat
      actionExecutor.resolveAllCombat(map,c, view1, view2);
    }

    //Play turn with moveAction
    private void play_move() throws ClassNotFoundException, IOException{
        //get the move action
        MoveAction moveAction = (MoveAction) objIstream.readObject();

        //System.out.println("Move action from Player " + moveAction.player_id);
        //Check if move is valid
        String res = actionValidator.isValid(moveAction, map);
        
        if (res != null) {
          //Move is invalid
          dataOtream.writeUTF(res);
        } 
        else {
          //Move is valid
          //Execute the move
          dataOtream.writeUTF("correct");
          actionExecutor.execute(moveAction, map);
        }
    }

    private void play_upgrade() throws ClassNotFoundException, IOException{
      //get the upgrade action
      UpgradeAction upgradeAction = (UpgradeAction) objIstream.readObject();
      String res = actionValidator.isValid(upgradeAction, map);

      if(res != null){
        //Upgrade is invalid
        dataOtream.writeUTF(res);
      }
      else{
        //Upgrade is valid, execute
        dataOtream.writeUTF("correct");
        //System.out.println(upgradeAction.toString());
        actionExecutor.execute(upgradeAction, map);
      }
    }

    private void play_attack(ArrayList<AttackAction> attackActionList) throws ClassNotFoundException, IOException{
        //Get attack action
        AttackAction attackAction = (AttackAction) objIstream.readObject();
        //System.out.println("Attack action from Player " + attackAction.player_id);
        
        //Check if attack is valid
        String res = actionValidator.isValid(attackAction, map);
       
        if (res != null) {
          //Attack is invalid
          dataOtream.writeUTF(res);
        } else {
          //Attack is valid, execute it
          dataOtream.writeUTF("correct");
          attackActionList.add(attackAction);
        }
    }

    private void play_spy() throws ClassNotFoundException, IOException{
      SpyAction action = (SpyAction) objIstream.readObject();
      String res = actionValidator.isValid(action, map);

      if (res != null) {
        //spy is invalid
        dataOtream.writeUTF(res);
      } else {
        //spy is valid, set it
        dataOtream.writeUTF("correct");
        actionExecutor.setAction(action, map, turn_no);
        spies.add(action);
      }
    }

    private void play_spyMove() throws ClassNotFoundException, IOException{
      SpyMoveAction action = (SpyMoveAction) objIstream.readObject();
      String res = actionValidator.isValid(action, map);

      if (res != null) {
        //spy is invalid
        dataOtream.writeUTF(res);
      } else {
        //spy is valid, set it
        dataOtream.writeUTF("correct");
        actionExecutor.execute(action, map);
      }
    }

    private void play_cloak() throws ClassNotFoundException, IOException{
      CloakAction action = (CloakAction) objIstream.readObject();
      String res = actionValidator.isValid(action, map);

      if (res != null) {
        //spy is invalid
        dataOtream.writeUTF(res);
      } else {
        //spy is valid, set it
        dataOtream.writeUTF("correct");
        actionExecutor.execute(action, map, turn_no);
        cloaks.add(action);
      }
    }

    //Play the turn
    private void play_turn(int index, ArrayList<AttackAction> attackActionList) throws IOException, ClassNotFoundException{
        while (true) {

          // Receive Actions
          //System.out.println("Try to fetch action from player " + index);

          // Get the action type
          String action = dataItream.readUTF();

          // MOVE ACTION
          if (action.equals("Move")) {
            play_move();
          }
          // ATTACK ACTION
          else if (action.equals("Attack")) {
            play_attack(attackActionList);
          }
          //UPGRADE ACTION
          else if (action.equals("Upgrade")){
            play_upgrade();
          }
          //SPY ACTION
          else if (action.equals("Spy")){
            play_spy();
          }
          //CLOAKTING ACTION
          else if (action.equals("Cloak")){
            play_cloak();
          }

          else if(action.equals("SpyMove")){
            play_spyMove();
          }

          //DONE ACTION
          else if(action.equals("Done")){
            break;
          }
        }
    }

    public void doAction(Map map, int num_player, ServerSock server_sock, Players players) throws IOException, ClassNotFoundException{
    /// Validation and execute Move Action
    this.actionValidator = new ActionValidator();
    this.actionExecutor = new ActionExecutor();

    //set class variables
    setVariables(players, num_player, map);

    //List to track exited users
    ArrayList<Integer> exit_losers = new ArrayList<Integer>();

    while (true) {      
      // get Map in String form 
      //String map_info = txt_map();
      String view1_str = view1.view();
      String view2_str = view2.view();
      //System.out.println(view1_str);

      // attack list init
      ArrayList<AttackAction> attackActionList = new ArrayList<>();

      //Flag to find winner
      boolean winner = false;

      //Play turn for each player
      for (int index = 0; index < num_player; index++) {
        
        //check if player is loser, has exited
        if(exit_losers.contains(index)){
          continue;
        }

        //Get streams for the player
        this.objIstream = server_sock.getObjIn(index);
        //ObjectOutputStream objOstream = server_sock.getObjOut(index);
        this.dataItream = server_sock.getDataIn(index);
        this.dataOtream = server_sock.getDataOut(index);

        // Send Map
        //System.out.println("Total_map : " + map_info);
        Region reg = map.getRegionById(index);
        String map_buf = new String();
        if(index == 0){
          map_buf = view1_str;
        }
        else{
          map_buf = view2_str;
        }
        map_buf += reg.getInfo();
        dataOtream.writeUTF(map_buf);

        // check if there is a winner
        winner = check_winner(index);
        if(winner){
            continue;
        }

        // send client his/her player status
        boolean is_loser = send_status(index);
        if(is_loser){
            //Loser found
            //Skip playing turn
            String opt = dataItream.readUTF();
            if(opt.equals("exit")){
              exit_losers.add(index);
            }
            continue;
        }

        //Play the turn
        play_turn(index, attackActionList);
      } 
      //Found winner 
      if(winner == true){
        break;
      }
      
      // Execute attacks-list
      do_combat(attackActionList);

      // add one unit to each area after each turn
      actionExecutor.addUnitToAllArea(1, map);

      //Update resources of all areas
      actionExecutor.updateResources(map);

      //end of turn
      turn_no++;

      //update spies
      for(Iterator<SpyAction> i = spies.iterator(); i.hasNext();){
        SpyAction spy = i.next();
        if(spy.hasReached(turn_no)){
          actionExecutor.execute(spy, map);
          i.remove();
        }
      }

      //update cloaks
      for(Iterator<CloakAction> i = cloaks.iterator(); i.hasNext();){
        CloakAction cloak = i.next();
        if(cloak.hasEnded(turn_no)){
          actionExecutor.removeCloak(cloak, map);
          i.remove();
        }
      }
    }

  }
}
