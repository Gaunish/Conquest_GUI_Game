package team5.risc.common;

import java.io.Serializable;

public class AreaNode implements Serializable{
  private String name;
  public AreaNode(String name){
    this.name = name;
  }
  public String getName(){
    return name;
  }
}
