package io.github.jamsesso.jsonlogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConcatenateExpressionTests {
  private static final JsonLogic jsonLogic = JsonLogic.getInstance();

  @Test
  public void testCat() throws JsonLogicException {
    assertEquals("hello world 2!", jsonLogic.apply("{\"cat\": [\"hello\", \" world \", 2, \"!\"]}", null));
    assertEquals("pi is 3.14", jsonLogic.apply("{\"cat\": [\"pi is \", 3.14159]}", null));
  }
}
