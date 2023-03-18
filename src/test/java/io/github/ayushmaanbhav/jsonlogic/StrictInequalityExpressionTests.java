package io.github.ayushmaanbhav.jsonlogic;

import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class StrictInequalityExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testSameValueSameType() throws JsonLogicException {
        assertEquals(false, jsonLogic.apply("{\"!==\": [1, 1.0]}", null));
    }

    @Test
    public void testSameValueDifferentType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"!==\": [1, \"1\"]}", null));
    }
}
