package team5.risc.common;

import java.io.Serializable;

public class CloakAction {
    public int player_id, turn;
    public String area;
    public int cost, no_turn;

    public CloakAction(String area){
        this.area = area;
        turn = -1;
        cost = 30;
        no_turn = 3;
    }

    public boolean hasEnded(int curr_turn){
        return (curr_turn - turn) >= no_turn;
    }

    @Override
    public String toString(){
        return player_id + ": " + area;
    }
}
