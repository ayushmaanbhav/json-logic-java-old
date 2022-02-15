package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ConcatenateExpression implements PreEvaluatedArgumentsExpression {
  public static final ConcatenateExpression INSTANCE = new ConcatenateExpression();

  private ConcatenateExpression() {
    // Use INSTANCE instead.
  }

  @Override
  public String key() {
    return "cat";
  }

  @Override
  public Object evaluate(List arguments, Object data, JsonLogicConfig jsonLogicConfig) throws JsonLogicEvaluationException {
    return arguments.stream()
      .map(obj -> {
        if (obj instanceof Double && obj.toString().endsWith(".0")) {
          return ((Double) obj).intValue();
        }
        else if (obj instanceof BigDecimal) {
          return parseBigDecimal((BigDecimal) obj,jsonLogicConfig);
        }
        return obj;
      })
      .map(Object::toString)
      .collect(Collectors.joining());
  }

  private Object parseBigDecimal(BigDecimal number,JsonLogicConfig jsonLogicConfig) {
      int scale = number.stripTrailingZeros().scale();
      return new BigDecimal(number.toString()).setScale(scale, jsonLogicConfig.getRoundingMode());
  }
}
