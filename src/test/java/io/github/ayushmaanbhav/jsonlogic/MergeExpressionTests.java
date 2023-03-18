package io.github.ayushmaanbhav.jsonlogic;

import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MergeExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testMerge() throws JsonLogicException {
        Object result = jsonLogic.apply("{\"merge\": [[1, 2], [3, 4]]}", null);

        assertEquals(4, ((List) result).size());
        assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), ((List) result).get(0));
        assertEquals(TestUtil.parseDoubleToBigDecimal(2.0), ((List) result).get(1));
        assertEquals(TestUtil.parseDoubleToBigDecimal(3.0), ((List) result).get(2));
        assertEquals(TestUtil.parseDoubleToBigDecimal(4.0), ((List) result).get(3));
    }

    @Test
    public void testMergeWithNonArrays() throws JsonLogicException {
        Object result = jsonLogic.apply("{\"merge\": [1, 2, [3, 4]]}", null);

        assertEquals(4, ((List) result).size());
        assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), ((List) result).get(0));
        assertEquals(TestUtil.parseDoubleToBigDecimal(2.0), ((List) result).get(1));
        assertEquals(TestUtil.parseDoubleToBigDecimal(3.0), ((List) result).get(2));
        assertEquals(TestUtil.parseDoubleToBigDecimal(4.0), ((List) result).get(3));
    }
}
