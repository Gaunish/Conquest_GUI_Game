package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TextDisplayMapTest {
  @Test
  public void test_display() {
    TextDisplayMap txt = new TextDisplayMap(System.out);
    String ans = new String();
    ans += "Player 0:\n" + "10 units in area0 (next to: area3,area2)\n"
        + "12 units in area3 (next to: area0,area2,area5)\n" + "14 units in area6 (next to: area5,area7,area1,area4)\n"
        + "Player 1:\n" + "13 units in area1 (next to: area4,area6)\n"
        + "8 units in area4 (next to: area5,area1,area6)\n" + "3 units in area7 (next to: area5,area8,area6)\n"
        + "Player 2:\n" + "6 units in area2 (next to: area0,area3,area5,area8)\n"
        + "5 units in area5 (next to: area2,area3,area8,area4,area6,area7)\n"
        + "3 units in area8 (next to: area2,area5,area7)\n";
    assertEquals(ans, txt.display(new Map()));

    txt.levelDisplay(new Map());
  }

}
