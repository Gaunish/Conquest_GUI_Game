package team5.risc.common;

import java.util.HashSet;

public class Players {
    //winner is -1 initially
    int winner;
    int total_no;
    HashSet<Integer> losers;

    public Players(int no){
        this.total_no = no;
        this.winner = -1;
        losers = new HashSet<>();
    }

    public int get_winner(Map map, int num_player){
        if(winner == -1){
            for(int id = 0; id < num_player; id++){
                boolean is_winner = has_won(map, id); 
                if(is_winner == true){
                    break;
                }
            }
        }
        return winner;
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

    public boolean has_won(Map map, int id){
        boolean is_win = map.is_winner(id);
        if(is_win == true){
            winner = id;            
        }
        return is_win;
    }
}
