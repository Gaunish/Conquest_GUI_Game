package team5.risc.common;

import java.io.Serializable;
import java.util.ArrayList;

import org.checkerframework.checker.units.qual.Area;

public class View implements Serializable {
    private Region region_own, region_enemy;
    private int id;
    private Map map;
    private ArrayList<AreaView> areas;

    public View(){
        areas = new ArrayList<>();
    }

    public View(Region r_own, Region r_enemy, int id, Map m, int no_areas){
        this.region_own = r_own;
        this.region_enemy = r_enemy;
        this.id = id;
        this.map = m;
        areas = new ArrayList<>();
        initAreas(no_areas);
    }

    public void setBuffer(AreaNode buffer){
        String name = buffer.getName();
        int id = Integer.parseInt(name.substring(4, name.length()));
        AreaView view = areas.get(id);
        view.setBuffer(buffer);
    }

    public void initAreas(int no_areas){
        for(int i = 0; i < no_areas; i++){
            areas.add(new AreaView(map.getAreaNodeByName("area" + i), id, map));
        }
    }

    public String view(){
        String txt = new String();
        for(AreaView area : areas){
          area.setGrey(isReachable(area));
          txt += area.view();
        }
        return txt;
    }

    public Boolean isReachable(AreaView areaView){
        AreaNode area = areaView.getArea();
        for(AreaNode a : region_own.getAreas()){
            if(area == a || a.isNeighbor(area)){
                return true;
            }
        }
        return false;
    }

}
