package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class LogicExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testOr() throws JsonLogicException {
        assertEquals("a", jsonLogic.apply("{\"or\": [0, false, \"a\"]}", null));
    }

    @Test
    public void testAnd() throws JsonLogicException {
        assertEquals("", jsonLogic.apply("{\"and\": [true, \"\", 3]}", null));
    }
}
