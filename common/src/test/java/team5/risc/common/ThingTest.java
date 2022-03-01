package team5.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ThingTest {
  @Test
  public void test_thing() {
    Thing t = new Thing("x");
    assertEquals("A thing in the x", t.toString());
  }

}
