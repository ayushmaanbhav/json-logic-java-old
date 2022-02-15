package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class AllExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

  @Test
  public void testEmptyArray() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"all\": [[], {\">\": [{\"var\": \"\"}, 0]}]}", null));
  }

  @Test
  public void testAll() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"all\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 0]}]}", null));
    assertEquals(false, jsonLogic.apply("{\"all\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 1]}]}", null));
  }
}
