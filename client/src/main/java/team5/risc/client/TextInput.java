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
    out.println(str + ", Try again!");
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
        finally{
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
    String prompt = "You are the "+ name +" , what would you like to do?\n(M)ove\n(A)ttack\n(D)one\n";
 
    String user_in = "";

    //Be in while loop till valid input
    while(true){
      try{
        //print prompt
        out.print(prompt);

        //Get input
        user_in = in.nextLine();

        if(!user_in.equals("M") && !user_in.equals("A") && !user_in.equals("D")){
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

  public MoveAction getMove(){
    String prompt = "Enter the source, destination, no of players\n";
    MoveAction move;
    //Be in while loop till valid input
    while(true){
      try{
        //print prompt
        out.print(prompt);

        //Get input
        String user_in = in.nextLine();
        String[] values = user_in.split(" ");

        //Invalid input 1
        if(values.length != 3){
          error_msg("Invalid no of args");
          continue;
        }
        
        int no = Integer.parseInt(values[2]);

        //Invalid input 2
        if(no <= 0){
          error_msg("Negative no of input");
          continue;
        }
        
        move = new MoveAction(values[0], values[1], no);
        break;
      }
      catch(Exception e){
        error_msg("Invalid input");
        continue;
      }
    }

    return move;
  }

}
