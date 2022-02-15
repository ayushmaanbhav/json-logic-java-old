package io.github.jamsesso.jsonlogic.utils;

import io.github.jamsesso.jsonlogic.JsonLogic;

import java.math.BigDecimal;

public class ValueParser {


    public static BigDecimal parseDoubleToBigDecimal(Double value){
        return  BigDecimal.valueOf(value).setScale(JsonLogic.getInstance().getJsonLogicConfig().getScale(),JsonLogic.getInstance().getJsonLogicConfig().getRoundingMode());
    }
    public static BigDecimal setScaleAndPrecision(BigDecimal value,JsonLogicConfig jsonLogicConfig){
        return value.setScale(jsonLogicConfig.getScale(),jsonLogicConfig.getRoundingMode());
    }
    public static BigDecimal parseStringToBigDecimal(String value,JsonLogicConfig jsonLogicConfig){
        return new BigDecimal(value).setScale(jsonLogicConfig.getScale(),jsonLogicConfig.getRoundingMode());
    }
}
