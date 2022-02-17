package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class InequalityExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testDifferentValueSameType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"!=\": [1, 2]}", null));
    }

    @Test
    public void testSameValueDifferentType() throws JsonLogicException {
        assertEquals(false, jsonLogic.apply("{\"!=\": [1.0, \"1\"]}", null));
    }
}
