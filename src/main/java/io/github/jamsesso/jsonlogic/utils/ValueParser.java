package io.github.jamsesso.jsonlogic.utils;

import io.github.jamsesso.jsonlogic.JsonLogic;

import java.math.BigDecimal;

public class ValueParser {

    public static BigDecimal parseStringToBigDecimal(String value){
        return new BigDecimal(value).setScale(JsonLogic.initialize().getJsonLogicConfig().getScale(),JsonLogic.initialize().getJsonLogicConfig().getRoundingMode());
    }

    public static BigDecimal parseDoubleToBigDecimal(Double value){
        return  BigDecimal.valueOf(value).setScale(JsonLogic.initialize().getJsonLogicConfig().getScale(),JsonLogic.initialize().getJsonLogicConfig().getRoundingMode());
    }
    public static BigDecimal setScaleAndPrecision(BigDecimal value){
        return value.setScale(JsonLogic.initialize().getJsonLogicConfig().getScale(),JsonLogic.initialize().getJsonLogicConfig().getRoundingMode());
    }
}
