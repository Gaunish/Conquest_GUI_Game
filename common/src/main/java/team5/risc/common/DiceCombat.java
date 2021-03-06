package team5.risc.common;

import java.util.Random;

public class DiceCombat implements Combat {
  int dice_sides;
  int no_unit;

  public DiceCombat(int dice_sides, int no_unit) {
    this.dice_sides = dice_sides;
    this.no_unit = no_unit;
  }

  public Army doCombat(Army defender, Army attacker) {
    Army winner;

    // combat resolution
    while (true) {
      // Attacker wins combat
      if (defender.getUnitNum() <= 0) {
        winner = attacker;
        break;
      }
      // Defender wins combat
      if (attacker.getUnitNum() <= 0) {
        winner = defender;
        break;
      }

      // get dice roll for both
      int roll_attack = getRandom() + attacker.getBonus();
      int roll_defend = getRandom() + defender.getBonus();

      if (roll_defend < roll_attack) {
        // Defender loses round
        defender.removeUnit(no_unit);
      } else {
        // Attacker loses round
        attacker.removeUnit(no_unit);
      }
    }

    return winner;
  }

  private int getRandom() {
    Random random = new Random();
    return random.nextInt(dice_sides) + 1;
  }

  /*
   * ------------------------------------------------------------- Getter and
   * Setter methods -------------------------------------------------------------
   */
  public int getDice() {
    return dice_sides;
  }

  public void setDice(int sides) {
    this.dice_sides = sides;
  }

  public int getUnit() {
    return no_unit;
  }

  public void setUnit(int no) {
    this.no_unit = no;
  }
}
