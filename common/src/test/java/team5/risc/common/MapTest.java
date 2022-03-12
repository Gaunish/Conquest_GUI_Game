package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MapTest {
  @Test
  public void test_Map() {
    Map m = new Map(2);
    assertEquals("area0", m.getAreasName().get(0));
    assertEquals("area1", m.getAreasName().get(1));

    ArrayList<AreaNode> areas = m.getAreas();
    assertEquals("area0", areas.get(0).getName());
    assertEquals(-1, areas.get(0).getOwnerId());

    Map m1 = new Map(4, 2);
    ArrayList<AreaNode> areas1 = m1.getAreas();
    assertEquals(0, areas1.get(0).getOwnerId());
    assertEquals(1, areas1.get(1).getOwnerId());
    assertEquals(0, areas1.get(2).getOwnerId());
    assertEquals(1, areas1.get(3).getOwnerId());
  }

  @Test
  public void test_region() {
    Map m = new Map(4, 2);
    Region r0 = m.getInitRegions().get(0);
    Region r1 = m.getInitRegions().get(1);
    assertEquals(r0.toString(), "-1: [area0, area2]");
    assertEquals(r1.toString(), "-1: [area1, area3]");
  }

}
