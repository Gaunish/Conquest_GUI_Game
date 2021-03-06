package team5.risc.common;

import java.io.Serializable;

//MetaInfo class for placement
public class MetaInfoPlacement implements Serializable{
  public String inform_unit;
  public String place_unit;

  public MetaInfoPlacement(int no_units){
    inform_unit = "You have been assigned "+ no_units +" units by server.\n";
  }

  public void placeStr(String area){
    place_unit = "How many units do you want to place in " + area + "?\n";
  }
}
