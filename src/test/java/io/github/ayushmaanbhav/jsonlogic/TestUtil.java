package io.github.ayushmaanbhav.jsonlogic;

import org.junit.Ignore;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Ignore
public class TestUtil {
    public static BigDecimal parseDoubleToBigDecimal(Double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
