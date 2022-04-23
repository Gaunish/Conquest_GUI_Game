package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class AreaViewTest {
    @Test
    public void test_all() {
        Map m = new Map(false);
        AreaNode a = m.getAreaNodeByName("area0");
        AreaView view1 = new AreaView(a, 0, m);
        AreaView view2 = new AreaView(a, 1, m);


        view1.setGrey(true);
        view2.setGrey(true);
        assertEquals("Reachable: true\n", view1.getGrey());
        assertEquals("Spy: false\n", view1.getSpy());
        assertEquals(a, view1.getArea());
        assertEquals(false, view1.isOld());
        assertEquals("Cloak: false\n", view1.getCloaking());
        assertEquals("Old: false\n", view1.getOld());
        assertEquals("Spy: false\n", view2.getSpy());
        assertEquals("Reachable: true\n", view2.getGrey());
        assertEquals(a, view2.getArea());
        assertEquals("Cloak: false\n", view2.getCloaking());
        assertEquals(false, view2.isOld());
        assertEquals("Old: false\n", view2.getOld());
        assertNotEquals("", view1.view());
        assertNotEquals("", view2.view());

        a.setCloaking(true);
        a.setSpy(true);
        assertEquals("Cloak: false\n", view1.getCloaking());
        assertEquals("Spy: false\n", view1.getSpy());
        assertEquals("Cloak: true\n", view2.getCloaking());
        assertEquals("Spy: true\n", view2.getSpy());

        a.setSpy(false);
        AreaNode buf = m.getAreaNodeByName("area1");
        view1.setBuffer(buf);
        view2.setBuffer(buf);
        assertEquals(null, view2.getOldArea());
        assertEquals(buf, view1.getOldArea());

        view1.setBuffer(a);
        view2.setBuffer(a);

        assertEquals(a, view2.getOldArea());
        assertEquals(null, view1.getOldArea());

        view1.setGrey(false);
        view2.setGrey(false);
        assertEquals(true, view2.isOld());
        assertEquals(false, view1.isOld());
        assertNotEquals("", view1.view());
        assertNotEquals("", view2.view());
    }
    
}
