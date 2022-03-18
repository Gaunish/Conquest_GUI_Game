package team5.risc.common;

public class MoveAction extends SimpleAction {
    public MoveAction(int player_id, 
                      String src,
                      String dst,
                      int unit) {
        super(player_id, src, dst, unit);
    }
}
