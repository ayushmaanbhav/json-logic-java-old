package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class EqualityExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testSameValueSameType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [1, 1]}", null));
    }

    @Test
    public void testSameValueDifferentType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [1, 1]}", null));
    }

    @Test
    public void testDifferentValueDifferentType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [[], false]}", null));
    }

    @Test
    public void testEmptyStringAndZeroComparison() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [\" \", 0]}", null));
    }
}
