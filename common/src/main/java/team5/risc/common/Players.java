package team5.risc.common;

import java.util.HashSet;

public class Players {
    int winner;
    int total_no;
    HashSet<Integer> losers;

    public Players(int no){
        this.total_no = no;
        losers = new HashSet<>();
    }

    public void add_loser(int id){
        losers.add(id);
    }

    public boolean has_lost(Map map, int id){
        //check if player is present in list
        boolean in_list = losers.contains(id);

        boolean is_loser = map.is_loser(id);

        if(in_list == true){
            return true;
        }
        //if loser and not in list add to it
        if(is_loser == true){
            if(in_list == false){
                add_loser(id);
            }
            return true;
        }
        return false;
    }
}
