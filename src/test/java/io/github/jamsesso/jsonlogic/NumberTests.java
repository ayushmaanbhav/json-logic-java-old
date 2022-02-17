package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import org.junit.Assert;
import org.junit.Test;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class NumberTests {
    @Test
    public void testConvertAllNumericInputToDouble() throws JsonLogicException {
        JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));
        Map<String, Number> numbers = new HashMap<String, Number>() {{
            put("double", 1D);
            put("float", 1F);
            put("int", 1);
            put("short", (short) 1);
            put("long", 1L);
        }};

        Assert.assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), jsonLogic.apply("{\"var\": \"double\"}", numbers));
        Assert.assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), jsonLogic.apply("{\"var\": \"float\"}", numbers));
        Assert.assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), jsonLogic.apply("{\"var\": \"int\"}", numbers));
        Assert.assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), jsonLogic.apply("{\"var\": \"short\"}", numbers));
        Assert.assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), jsonLogic.apply("{\"var\": \"long\"}", numbers));
    }
}
