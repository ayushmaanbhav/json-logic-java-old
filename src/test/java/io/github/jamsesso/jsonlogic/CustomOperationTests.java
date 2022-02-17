package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Assert;
import org.junit.Test;

import java.math.RoundingMode;

public class CustomOperationTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testCustomOp() throws JsonLogicException {
        jsonLogic.addOperation("greet", args -> String.format("Hello %s!", args[0]));
        Assert.assertEquals("Hello json-logic!", jsonLogic.apply("{\"greet\": [\"json-logic\"]}", null));
    }

    @Test
    public void testCustomOpWithUppercaseLetter() throws JsonLogicException {
        jsonLogic.addOperation("Greet", args -> String.format("Hello %s!", args[0]));
        Assert.assertEquals("Hello json-logic!", jsonLogic.apply("{\"Greet\": [\"json-logic\"]}", null));
    }
}
