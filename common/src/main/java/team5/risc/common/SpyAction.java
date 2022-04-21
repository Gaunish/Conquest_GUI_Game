package team5.risc.common;

import java.io.Serializable;

public class SpyAction implements Action, Serializable {
    public int player_id;
    public String src, dest;
    public int turn, distance, cost;

    public SpyAction(String src, String dest){
        this.src = src;
        this.dest = dest;
        turn = -1;
        distance = -1;
        cost = 40;
    }

    public boolean hasReached(int curr_turn){
        return (curr_turn - turn) >= distance;
    }
    
    @Override
    public String toString(){
        return player_id + ": " + src + "->" + "dest";
    }
}
