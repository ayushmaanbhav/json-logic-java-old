package io.github.ayushmaanbhav.jsonlogic;

import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class ConcatenateExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testCat() throws JsonLogicException {
        assertEquals("hello world 20.01!", jsonLogic.apply("{\"cat\": [\"hello\", \" world \", 20.006, \"!\"]}", null));
        assertEquals("pi is 3.14", jsonLogic.apply("{\"cat\": [\"pi is \", 3.14159]}", null));
        assertEquals("pi is 3.14159", jsonLogic.apply("{\"cat\": [\"pi is \", \"3.14159\"]}", null));
        assertEquals("pi is 3", jsonLogic.apply("{\"cat\": [\"pi is \", 3.0]}", null));
    }
}
