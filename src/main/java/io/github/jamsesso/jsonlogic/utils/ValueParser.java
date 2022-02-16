package io.github.jamsesso.jsonlogic.utils;

import io.github.jamsesso.jsonlogic.JsonLogicException;

import java.math.BigDecimal;

public class ValueParser {

    public static BigDecimal setScaleAndPrecision(BigDecimal value,JsonLogicConfig jsonLogicConfig){
        validateConfig(jsonLogicConfig);
        return value.setScale(jsonLogicConfig.getScale(),jsonLogicConfig.getRoundingMode());
    }
    public static BigDecimal parseStringToBigDecimal(String value,JsonLogicConfig jsonLogicConfig){
        validateConfig(jsonLogicConfig);
        return new BigDecimal(value).setScale(jsonLogicConfig.getScale(),jsonLogicConfig.getRoundingMode());
    }

    private static void validateConfig(JsonLogicConfig jsonLogicConfig){
        if(jsonLogicConfig==null || jsonLogicConfig.getRoundingMode()==null){
            throw  new RuntimeException("Invalid JsonLogic configuration");
        }
    }
}
