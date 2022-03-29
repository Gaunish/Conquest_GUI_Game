package team5.risc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.Serializable;

public class Region implements Serializable {
  private int owner_id;
  private LinkedHashSet<AreaNode> areas;
  private int food_resource;
  private int tech_resource;

  public Region() {
    this.owner_id = -1; // no owner at the beginning
    this.areas = new LinkedHashSet<AreaNode>();
    this.food_resource = 0;
    this.tech_resource = 0;
  }

  public void setOwnerId(int owner_id) {
    this.owner_id = owner_id;
  }

  public void setInitUnit(AreaNode the_area, int unit_num) throws RuntimeException {
    if (areas.contains(the_area)) {
      the_area.setDefender(new IntArmy(this.owner_id, unit_num));
    } else {
      throw new UnsupportedOperationException("The area doesn't belong to this onwer");
    }
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

  public int getFoodResource() {
    return food_resource;
  }

  public int getTechResource() {
    return tech_resource;
  }

  public void consumeFood(int to_remove) throws RuntimeException {
    if (to_remove > food_resource) {
      throw new UnsupportedOperationException("No enough food");
    } else {
      food_resource -= to_remove;
    }
  }

  public void consumeTech(int to_remove) throws RuntimeException {
    if (to_remove > tech_resource) {
      throw new UnsupportedOperationException("No enough tech");
    } else {
      tech_resource -= to_remove;
    }
  }

  public void collectResource() {
    Iterator<AreaNode> it = areas.iterator();
    while (it.hasNext()) {
      AreaNode cur = it.next();
      food_resource += cur.getFood();
      tech_resource += cur.getTech();
    }
  }

  public String toString() {
    return owner_id + ": " + getAreasName();

  }
}
