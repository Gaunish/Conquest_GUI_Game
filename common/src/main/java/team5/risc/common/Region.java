package team5.risc.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.io.Serializable;

public class Region implements Serializable{
  private int owner_id;
  private LinkedHashSet<AreaNode> areas;

  public Region() {
    this.owner_id = -1; // no owner at the beginning
    this.areas = new LinkedHashSet<AreaNode>();
  }

  public void set_owner_id(int owner_id) {
    this.owner_id = owner_id;
  }

  public void set_init_unit(AreaNode the_area, int unit_num) throws RuntimeException {
    if (areas.contains(the_area)) {
      the_area.setDefender(new IntArmy(owner_id, unit_num));
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

  public String toString() {
    return owner_id + ": " + getAreasName();

  }
}