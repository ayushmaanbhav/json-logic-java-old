package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.ValueParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MergeExpressionTests {
  private static final JsonLogic jsonLogic = JsonLogic.initialize();

  @Test
  public void testMerge() throws JsonLogicException {
    Object result = jsonLogic.apply("{\"merge\": [[1, 2], [3, 4]]}", null);

    assertEquals(4, ((List) result).size());
    assertEquals(ValueParser.parseDoubleToBigDecimal(1.0), ((List) result).get(0));
    assertEquals(ValueParser.parseDoubleToBigDecimal(2.0), ((List) result).get(1));
    assertEquals(ValueParser.parseDoubleToBigDecimal(3.0), ((List) result).get(2));
    assertEquals(ValueParser.parseDoubleToBigDecimal(4.0), ((List) result).get(3));
  }

  @Test
  public void testMergeWithNonArrays() throws JsonLogicException {
    Object result = jsonLogic.apply("{\"merge\": [1, 2, [3, 4]]}", null);

    assertEquals(4, ((List) result).size());
    assertEquals(ValueParser.parseDoubleToBigDecimal(1.0), ((List) result).get(0));
    assertEquals(ValueParser.parseDoubleToBigDecimal(2.0), ((List) result).get(1));
    assertEquals(ValueParser.parseDoubleToBigDecimal(3.0), ((List) result).get(2));
    assertEquals(ValueParser.parseDoubleToBigDecimal(4.0), ((List) result).get(3));
  }
}
