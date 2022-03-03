package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  public void test_Map() {
    Map m = new Map(1);
    assertEquals("area0", m.getAreasName().get(0));
  }

}
