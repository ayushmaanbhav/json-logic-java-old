package io.github.ayushmaanbhav.jsonlogic;

import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import org.junit.Test;

import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MathExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic(new JsonLogicConfig(2, RoundingMode.HALF_UP));

    @Test
    public void testAdd() throws JsonLogicException {
        String json = "{\"+\":[4,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(6.0), result);
    }

    @Test
    public void testMultiAdd() throws JsonLogicException {
        String json = "{\"+\":[2,2,2,2,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(10.0), result);
    }

    @Test
    public void testSingleAdd() throws JsonLogicException {
        String json = "{\"+\" : \"3.14\"}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(3.14), result);
    }

    @Test
    public void testSubtract() throws JsonLogicException {
        String json = "{\"-\":[4,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(2.0), result);
    }

    @Test
    public void testSingleSubtract() throws JsonLogicException {
        String json = "{\"-\": 2 }";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(-2.0), result);
    }

    @Test
    public void testMultiply() throws JsonLogicException {
        String json = "{\"*\":[4,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(8.0), result);
    }

    @Test
    public void testMultiMultiply() throws JsonLogicException {
        String json = "{\"*\":[2,2,2,2,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(32.0), result);
    }

    @Test
    public void testDivide() throws JsonLogicException {
        String json = "{\"/\":[4,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(2.0), result);
    }

    @Test
    public void testDivideBy0() throws JsonLogicException {
        String json = "{\"/\":[4,0]}";
        assertThrows(JsonLogicEvaluationException.class, () -> {jsonLogic.apply(json, null);});
    }

    @Test
    public void testModulo() throws JsonLogicException {
        String json = "{\"%\": [101,2]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), result);
    }

    @Test
    public void testMin() throws JsonLogicException {
        String json = "{\"min\":[1,2,3]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(1.0), result);
    }

    @Test
    public void testMax() throws JsonLogicException {
        String json = "{\"max\":[1,2,3]}";
        Object result = jsonLogic.apply(json, null);

        assertEquals(TestUtil.parseDoubleToBigDecimal(3.0), result);
    }

    @Test
    public void testDivideSingleNumber() throws JsonLogicException {
        assertEquals(null, jsonLogic.apply("{\"/\": [0]}", null));
    }
}
