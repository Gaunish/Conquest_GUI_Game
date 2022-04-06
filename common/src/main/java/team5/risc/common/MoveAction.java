package team5.risc.common;

public class MoveAction extends SimpleAction {
    public MoveAction(int player_id,
            String src,
            String dst,
            int index,
            int unit) {
        super(player_id, src, dst, index, unit);
    }

    public MoveAction(int player_id, String src, String dst, int unit) {
        super(player_id, src, dst, 0, unit);
    }
}
