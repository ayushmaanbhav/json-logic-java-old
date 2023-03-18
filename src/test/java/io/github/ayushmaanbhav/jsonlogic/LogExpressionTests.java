package io.github.ayushmaanbhav.jsonlogic;

import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class LogExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testDoesLog() throws JsonLogicException {
        assertEquals("hello world", jsonLogic.apply("{\"log\": \"hello world\"}", null));
    }
}
