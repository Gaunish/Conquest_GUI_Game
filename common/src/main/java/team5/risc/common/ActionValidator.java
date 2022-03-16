package team5.risc.common;

import javax.sound.sampled.UnsupportedAudioFileException;

public class ActionValidator {
  public String isValid(MoveAction a, Map map) {
    if (a.player_id != a.source.getOwnerId()) {
      return "Player " + a.player_id + " has no access to " + a.source.getName() + ", which is owned by Player "
          + a.source.getOwnerId();
    }
    if (a.source.getOwnerId() != a.destination.getOwnerId()) {
      return a.destination.getName() + " belongs to Player" + a.destination.getOwnerId();
    }
    if (a.source.getDefenderUnit() < a.num_unit) {
      return a.source.getName() + "doesn't have enough unit to move";
    }
    return null;
  }

  public String isValid(AttackAction a, Map map) {
    return null;
  }
}
