package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  public void test_Map() {
    Map m = new Map(2);
    assertEquals("area0", m.getAreasName().get(0));
    assertEquals("area1", m.getAreasName().get(1));
  }

}
