package io.github.jamsesso.jsonlogic;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterExpressionTests {
  private static final JsonLogic jsonLogic = JsonLogic.initialize();

  @Test
  public void testMap() throws JsonLogicException {
    String json = "{\"filter\": [\n" +
                  "  {\"var\": \"\"},\n" +
                  "  {\"==\": [{\"%\": [{\"var\": \"\"}, 2]}, 0]}\n" +
                  "]}";
    int[] data = new int[] {1, 2, 3, 4, 5, 6};
    Object result = jsonLogic.apply(json, data);

    assertEquals(3, ((List) result).size());
    assertEquals(new BigDecimal("2.00"), ((List) result).get(0));
    assertEquals(new BigDecimal("4.00"), ((List) result).get(1));
    assertEquals(new BigDecimal("6.00"), ((List) result).get(2));
  }
}
