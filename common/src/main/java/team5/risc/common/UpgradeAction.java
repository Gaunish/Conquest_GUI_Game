package team5.risc.common;

import java.io.Serializable;

public class UpgradeAction implements Action, Serializable{
    int player_id;
    private String area;
    private int index_army;
    private int no_units;

    public UpgradeAction(int player_id, String area, int index_army, int no_units){
        this.player_id = player_id;
        this.area = area;
        this.index_army = index_army;
        this.no_units = no_units;
    }

    public String toString(){
        return player_id + " " + area + " " + index_army + " " + no_units;
    }
}
