package io.github.jamsesso.jsonlogic.ast;

import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.utils.ValueParser;

import java.math.BigDecimal;

public class JsonLogicNumber implements JsonLogicPrimitive<BigDecimal> {
  private final BigDecimal value;

  public JsonLogicNumber(BigDecimal value) {
    this.value = value;
  }

  @Override
  public BigDecimal getValue() {
    return value;
  }

  @Override
  public JsonLogicPrimitiveType getPrimitiveType() {
    return JsonLogicPrimitiveType.NUMBER;
  }
}
