package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class SubstringExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testSubstringSingleArg() throws JsonLogicException {
        assertEquals("logic", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 4]}", null));
    }

    @Test
    public void testSubstringSingleNegativeArg() throws JsonLogicException {
        assertEquals("logic", jsonLogic.apply("{\"substr\": [\"jsonlogic\", -5]}", null));
    }

    @Test
    public void testSubstringDoubleArg() throws JsonLogicException {
        assertEquals("son", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 1, 3]}", null));
    }

    @Test
    public void testSubstringDoubleNegativeArg() throws JsonLogicException {
        assertEquals("log", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 4, -2]}", null));
    }

    @Test
    public void testSubstringSingleArgOutOfBounds() throws JsonLogicException {
        assertEquals("", jsonLogic.apply("{\"substr\": [\"jsonlogic\", -40]}", null));
    }

    @Test
    public void testSubstringDoubleArgOutOfBounds() throws JsonLogicException {
        assertEquals("", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 20, -40]}", null));
    }
}
