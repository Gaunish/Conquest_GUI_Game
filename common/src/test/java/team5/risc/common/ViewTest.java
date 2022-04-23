package team5.risc.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ViewTest {
    @Test
    public void test_all() {
        Map m = new Map(false);
        Region r1 = m.getRegionById(0);
        Region r2 = m.getRegionById(1);
        View view1 = new View(r1, r2, 0, m, 6);
        View view2 = new View(r2, r1, 1, m, 6); 

        assertEquals(1, view1.getId(m.getAreaNodeByName("area1")));
      
        AreaView a0 = view1.getAreaView(m.getAreaNodeByName("area0"));
        AreaView a1 = view2.getAreaView(m.getAreaNodeByName("area0"));
        view1.setBuffer(m.getAreaNodeByName("area0"));
        view2.setBuffer(m.getAreaNodeByName("area0"));
        
        a0.setGrey(false);
        a1.setGrey(false);

        assertEquals(false, a0.isOld());
        assertEquals(m.getAreaNodeByName("area0"), a1.getOldArea());
        assertEquals(true, a1.isOld());

        assertNotEquals("", view1.view());
        assertNotEquals("", view2.view());
        assertEquals(true, view1.isReachable(a0));
        assertEquals(true, view2.isReachable(a1));

        m = new Map(12, 2, false);
        r1 = m.getRegionById(0);
        r2 = m.getRegionById(1);
        view1 = new View(r1, r2, 0, m, 12);
        view2 = new View(r2, r1, 1, m, 12);

        assertEquals(10, view1.getId(m.getAreaNodeByName("area10")));
        a0 = view1.getAreaView(m.getAreaNodeByName("area0"));
        a1 = view2.getAreaView(m.getAreaNodeByName("area0"));
        AreaView a3 = view2.getAreaView(m.getAreaNodeByName("area3"));
        assertEquals(true, view1.isReachable(a0));
        assertEquals(false, view2.isReachable(a1));
        assertEquals(true, view2.isReachable(a3));
    }
    
}
