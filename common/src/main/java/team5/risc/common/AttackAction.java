package team5.risc.common;

public class AttackAction extends SimpleAction {
    public AttackAction(int player_id, 
                      String src,
                      String dst,
                      int unit,
                      Boolean terminated) {
        super(player_id, src, dst, unit, terminated);
    }
}
