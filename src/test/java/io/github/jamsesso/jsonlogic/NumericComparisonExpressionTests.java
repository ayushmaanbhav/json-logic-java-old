package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class NumericComparisonExpressionTests {
  private static final JsonLogic jsonLogic =new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

  @Test
  public void testLessThan() throws JsonLogicException {
    String json = "{\"<\" : [1, 2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  public void testLessThanOrEqual() throws JsonLogicException {
    String json = "{\"<=\" : [1, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  public void testGreaterThan() throws JsonLogicException {
    String json = "{\">\" : [2, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  public void testGreaterThanOrEqual() throws JsonLogicException {
    String json = "{\">=\" : [1, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  public void testBetweenExclusive() throws JsonLogicException {
    String json = "{\"<\" : [1, 2, 3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  public void testBetweenInclusive() throws JsonLogicException {
    String json = "{\"<=\" : [1, 1, 3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }
}
