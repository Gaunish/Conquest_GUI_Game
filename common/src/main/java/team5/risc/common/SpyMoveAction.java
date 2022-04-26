package team5.risc.common;

import java.io.Serializable;

public class SpyMoveAction implements Action, Serializable {
    public int player_id;
    public String src, dest;

    public SpyMoveAction(int player_id, String src, String dest){
        this.player_id = player_id;
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString(){
        return player_id + ": " + src + "->" + dest;
    }
}
