package team5.risc.client;

import team5.risc.common.*;

import java.util.Scanner;
import java.io.*;


public class TextInput implements Input{
  private Scanner in;
  private InputStream input;
  private PrintStream out;

  public TextInput(InputStream input, PrintStream output){
    this.input = input;
    this.out = output;
    this.in = new Scanner(input);
  }

  private void error_msg(String str){
    out.println(str + ", Try again!\n");
  }

  public int getPlacement(String area){
    int no = -1;

    //Be in while loop till valid input
    while(true){
        try{
          out.print(area);
          String line = in.nextLine();
          no = Integer.parseInt(line);
         }
        catch(Exception e){
          error_msg("Unexpected input");
          continue;
        }
        finally {
          if(no < 0){
            error_msg("Invalid input");
            continue;
          }
          break;
        }
    }
    return no;
  }

  //Get move
  public String getAction(String name){
    String prompt = "You are the "+ name +" , what would you like to do?\n(M)ove\n(A)ttack\n(U)pgrade\n(D)one\n";
 
    String user_in = "";

    //Be in while loop till valid input
    while(true){
      try{
        //print prompt
        out.print(prompt);

        //Get input
        user_in = in.nextLine();
        //out.print("input is:"+user_in);
        if(!user_in.equals("M") && !user_in.equals("A") && !user_in.equals("D") && !user_in.equals("U")){
          error_msg("Invalid Input");
          continue;
        }
        break;
      }
      catch(Exception e){
        error_msg("Unexpected input");
        continue;
      }
    }

    return user_in;
  }

  public MoveAction getMove(int id){
    String prompt = "Enter the source, destination, no of players\n";

    String[] values = getValues(prompt);
    int no_unit = Integer.parseInt(values[2]);

    MoveAction move = new MoveAction(id, values[0], values[1], no_unit);
    
    return move;
  }

  public AttackAction getAttack(int id){
    String prompt = "Enter the source, destination, no of players\n";
    
    String[] values = getValues(prompt);
    int no_unit = Integer.parseInt(values[2]);

    AttackAction attack = new AttackAction(id, values[0], values[1], no_unit);
    
    return attack;
  }

  public UpgradeAction getUpgrade(int id, String area){
    String prompt = "Enter the army index, no of units to upgrade \n";
    String[] values = getUpgradeValues(prompt);
    int index = Integer.parseInt(values[0]);
    int no = Integer.parseInt(values[1]);

    UpgradeAction upgrade = new UpgradeAction(id, area, index, no);
    return upgrade;
  }

  private String[] getUpgradeValues(String prompt){
    String[] values;
     // Be in while loop till valid input
     while (true) {
      try {
        // print prompt
        out.print(prompt);

        // Get input
        String user_in = in.nextLine();
        values = user_in.split(" ");

        // Invalid input 1
        if (values.length != 2) {
          error_msg("Invalid no of args");
          continue;
        }
        
        int index = Integer.parseInt(values[0]);
        int no = Integer.parseInt(values[1]);

        // Invalid input 2
        if (index <= 0 || no <= 0) {
          error_msg("Negative input no");
          continue;
        }

        break;

      } catch (Exception e) {
        error_msg("Invalid input");
        continue;
      }
    }

    return values; 
  }

  public String getLoser(){
    String info = "You have lost the game\n";
    String prompt = "Do you want to continue watching the game or Exit?\n(W)atch\n(E)xit\n";
    String user_in = "";

    out.print(info);

    //Be in while loop till valid input
    while(true){
      try{
        //print prompt
        out.print(prompt);

        //Get input
        user_in = in.nextLine();
        //out.print("input is:"+user_in);
        if(!user_in.equals("W") && !user_in.equals("E")){
          error_msg("Invalid Input");
          continue;
        }
        break;
      }
      catch(Exception e){
        error_msg("Unexpected input");
        continue;
      }
    }
    
    //Based on input, get input to send to server
    String out = "";
    if(user_in.equals("W")){
      out = "watch";
    }
    else{
      out = "exit";
    }

    return out;
  }

  public String getArea(){
    String prompt = "Enter which area do you want to upgrade?\n";
    String user_in = "";

    //Be in while loop till valid input
    while(true){
      try{
        //print prompt
        out.print(prompt);

        //Get input
        user_in = in.nextLine();
        break;
      }
      catch(Exception e){
        error_msg("Unexpected input");
        continue;
      }
    }

    return user_in;
  }
  
  private String[] getValues(String prompt){
    String[] values;
 
    // Be in while loop till valid input
    while (true) {
      try {
        // print prompt
        out.print(prompt);

        // Get input
        String user_in = in.nextLine();
        values = user_in.split(" ");

        // Invalid input 1
        if (values.length != 3) {
          error_msg("Invalid no of args");
          continue;
        }

        int no = Integer.parseInt(values[2]);

        // Invalid input 2
        if (no <= 0) {
          error_msg("Negative no of input");
          continue;
        }

        break;

      } catch (Exception e) {
        error_msg("Invalid input");
        continue;
      }
    }

    return values; 
  }

}
