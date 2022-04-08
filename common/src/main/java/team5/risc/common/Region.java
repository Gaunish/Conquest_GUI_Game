package team5.risc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.Serializable;

public class Region implements Serializable {
  private int owner_id;
  private LinkedHashSet<AreaNode> areas;
  private Resources resource;

  public Region() {
    this.owner_id = -1; // no owner at the beginning
    this.areas = new LinkedHashSet<AreaNode>();

    resource = new Resources();

  }

  public void setOwnerId(int owner_id) {
    this.owner_id = owner_id;
  }

  public void setInitUnit(AreaNode the_area, int unit_num) throws RuntimeException {
    if (areas.contains(the_area)) {
      the_area.setDefender(new LevelArmy(this.owner_id, unit_num));
    } else {
      throw new UnsupportedOperationException("The area doesn't belong to this onwer");
    }
  }

  /*
  // Method to check if upgrade is valid
  public boolean isUpgradeValid(int lvl, int new_lvl, int no_unit, AreaNode area) {
    if (!areas.contains(area)) {
      return false;
    }
    return true;
  }*/
  

  // Method to upgrade unit in given area
  public void upgradeArmy(int lvl, int new_lvl, int no_unit, AreaNode area) {
    /*if (!isUpgradeValid(lvl, new_lvl, no_unit, area)
        || area.isUpgradeValid(lvl, new_lvl, no_unit, resource.getTech())) {
      return;
    }*/
    area.upgradeArmy(lvl, new_lvl, no_unit);
    int cost = area.costLevel(lvl, new_lvl);
    subTech(no_unit * cost);
  }

  // Method to increment total food/tech
  // after each turn
  public void incFoodTech() {
    resource.incFoodTech(areas);
  }

  public boolean checkFoodEnough(int food) {
    return resource.isFoodValid(food);
  }

  public boolean checkTechEnough(int tech) {
    return resource.isTechValid(tech);
  }

  // Method to subtract food
  public boolean subFood(int food) {
    return resource.subFood(food);
  }

  // Method to subtract tech
  public boolean subTech(int tech) {
    return resource.subTech(tech);
  }

  public void addArea(AreaNode to_add) {
    areas.add(to_add);
  }

  public void removeArea(AreaNode to_remove) {
    areas.remove(to_remove);
  }

  public Boolean noAreaLeft() {
    return areas.size() == 0;
  }

  public ArrayList<String> getAreasName() {
    ArrayList<String> areas_name = new ArrayList<String>();
    Iterator<AreaNode> it = areas.iterator();
    while (it.hasNext()) {
      areas_name.add(it.next().getName());
    }
    return areas_name;
  }

  public String toString() {
    return owner_id + ": " + getAreasName();
  }

  public String strDisplay(AreaNode a){
    String txt = new String();
    txt += a.getName() + "\n";
    txt += "Owner: Player" + owner_id + "\n"; 
    txt += a.displayRsrc();
    return txt;
  }

  public String strDisplay(){
    String txt = new String();
    for(AreaNode a : areas){
      txt += strDisplay(a);
    }
    return txt;
  }

  public String getInfo(){
    String txt = new String();
    txt += "Food: " + resource.getFood() + "\n";
    txt += "Tech: " + resource.getTech() + "\n\n";
    return txt;
  }
}
