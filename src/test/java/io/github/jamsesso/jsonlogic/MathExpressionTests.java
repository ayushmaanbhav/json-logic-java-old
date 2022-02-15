package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.ValueParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathExpressionTests {
  private static final JsonLogic jsonLogic = JsonLogic.getInstance();

  @Test
  public void testAdd() throws JsonLogicException {
    String json = "{\"+\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(6.0), result);
  }

  @Test
  public void testMultiAdd() throws JsonLogicException {
    String json = "{\"+\":[2,2,2,2,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(10.0), result);
  }

  @Test
  public void testSingleAdd() throws JsonLogicException {
    String json = "{\"+\" : \"3.14\"}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(3.14), result);
  }

  @Test
  public void testSubtract() throws JsonLogicException {
    String json = "{\"-\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(2.0), result);
  }

  @Test
  public void testSingleSubtract() throws JsonLogicException {
    String json = "{\"-\": 2 }";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(-2.0), result);
  }

  @Test
  public void testMultiply() throws JsonLogicException {
    String json = "{\"*\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(8.0), result);
  }

  @Test
  public void testMultiMultiply() throws JsonLogicException {
    String json = "{\"*\":[2,2,2,2,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(32.0), result);
  }

  @Test
  public void testDivide() throws JsonLogicException {
    String json = "{\"/\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(2.0), result);
  }

  @Test
  public void testDivideBy0() throws JsonLogicException {
    String json = "{\"/\":[4,0]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(Double.POSITIVE_INFINITY, result);
  }

  @Test
  public void testModulo() throws JsonLogicException {
    String json = "{\"%\": [101,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(1.0), result);
  }

  @Test
  public void testMin() throws JsonLogicException {
    String json = "{\"min\":[1,2,3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(1.0), result);
  }

  @Test
  public void testMax() throws JsonLogicException {
    String json = "{\"max\":[1,2,3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(ValueParser.parseDoubleToBigDecimal(3.0), result);
  }

  @Test
  public void testDivideSingleNumber() throws JsonLogicException {
    assertEquals(null, jsonLogic.apply("{\"/\": [0]}", null));
  }
}
