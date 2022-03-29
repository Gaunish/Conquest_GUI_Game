package team5.risc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import team5.risc.common.*;

public class PlacementPhase {
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;
    private DataInputStream dataInputStream;

    //Method to set DataOutputStream
    private void setOutStream(DataOutputStream dataOutputStream, ObjectOutputStream objectOutputStream, DataInputStream dataInputStream){
        this.dataOutputStream = dataOutputStream;
        this.objectOutputStream = objectOutputStream;
        this.dataInputStream = dataInputStream;
    }

    //Function to send id, assigned units
    private void write_client1(int id, MetaInfoPlacement strInfo) throws IOException{
        //Send id
        dataOutputStream.writeInt(id);
        dataOutputStream.flush();
  
        // send assigned units
        dataOutputStream.writeUTF(strInfo.inform_unit);
        dataOutputStream.flush();
    }

    //Send region to client
    private ArrayList<String> send_region(Region region) throws IOException{
        // get region in text form for player
        ArrayList<String> txt_region = region.getAreasName();
      
        // send region in text form
        objectOutputStream.writeObject(txt_region);
        objectOutputStream.flush();

        return txt_region;
    }

    //Send prompt to ask for no of units
    private void send_prompt(MetaInfoPlacement strInfo, String area) throws IOException{
        strInfo.placeStr(area);
        dataOutputStream.writeUTF(strInfo.place_unit);
        dataOutputStream.flush();
    }

    private int get_no_units() throws IOException{
        int no = (int) dataInputStream.readInt();
        return no;
    }

    //Method to check validity of input, update if correct
    private boolean check_units(int no_units, int no, int total_units, Region region, String area, Map map) throws IOException{
        AreaNode node = map.getAreaNodeByName(area);

        if ((no_units + no) > total_units) {
            String error = "Placement Invalid, Input: " + no + " Remaining: " + (total_units - no_units);
            dataOutputStream.writeUTF(error);
            return false;
          }
        else{
            no_units += no;
            region.setInitUnit(node, no);
            dataOutputStream.writeUTF("Success");
            return true;
        }
    }

    //Method to get one placement from a user
    private void get_one_placement(int id, int no_units, int total_units, String area, Region region, Map map){
        while(true){
            int no = -1;
            try {
              //Get no units
              no = get_no_units();

              //System.out.println("no:" + no);
              //System.out.println("Recieved " + no + " for " + area + " by Player " + id);

              //check validity of input, update if correct
              boolean is_valid = check_units(no_units, no, total_units, region, area, map);
              
              //Based on input, determine flow
              if (is_valid) {
                break;
              }
              else{
                continue;
              }
            } 
            catch (Exception e) {
              System.out.println(e);
              continue;
            }
          }
    }

    //Method to get all placements from a client
    private void get_placements(Map map, int id, int total_units, MetaInfoPlacement strInfo, ArrayList<Region> regions) throws IOException{
        Region region = regions.get(id);
        region.setOwnerId(id);
        ArrayList<String> txt_region = send_region(region);
  
        int no_units = 0;

        for (String area : txt_region) {
            //System.out.println("id : " + id + " Area: " + area);

            //Send prompt to client to ask for no of units
            send_prompt(strInfo, area);
    
            get_one_placement(id, no_units, total_units, area, region, map);
        }
    }

    public void DoPlacement(int num_player, ServerSock server_sock, Map map, int total_units) throws IOException{
        int id = 0;
        
        // class to send strings to clients
        MetaInfoPlacement strInfo = new MetaInfoPlacement(total_units);

        // init list of region of areas assigned to each player
        ArrayList<Region> regions = map.getRegions();

        for (int index = 0; index < num_player; index++) {
            //Get streams for the client
            ObjectOutputStream objectOutputStream = server_sock.getObjOut(index);
            DataInputStream dataInputStream = server_sock.getDataIn(index);
            DataOutputStream dataOutputStream = server_sock.getDataOut(index);
      
            //Set the streams
            setOutStream(dataOutputStream, objectOutputStream, dataInputStream);

            //send id, assigned unit to client
            write_client1(id, strInfo);
      
            //Get all placements from user
            get_placements(map, id, total_units, strInfo, regions);
            id++;
          }
    }
}
