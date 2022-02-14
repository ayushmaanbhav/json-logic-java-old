package io.github.jamsesso.jsonlogic.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class JsonLogicConfig {
    private final int scale;
    private final RoundingMode roundingMode;
    public JsonLogicConfig(int scale,RoundingMode roundingMode){
        this.scale=scale;
        this.roundingMode=roundingMode;
    }
    public int getScale(){
        return scale;
    }
    public RoundingMode getRoundingMode(){
        return this.roundingMode;
    }
}
