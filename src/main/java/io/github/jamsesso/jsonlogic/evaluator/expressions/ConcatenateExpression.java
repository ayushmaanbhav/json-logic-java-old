package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;

import java.math.BigDecimal;
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
  public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
    return arguments.stream()
      .map(obj -> {
        if (obj instanceof Double && obj.toString().endsWith(".0")) {
          return ((Double) obj).intValue();
        }
        else if (obj instanceof BigDecimal) {
          return parseBigDecimal((BigDecimal) obj);
        }
        return obj;
      })
      .map(Object::toString)
      .collect(Collectors.joining());
  }

  private Object parseBigDecimal(BigDecimal number) {
      int scale = number.stripTrailingZeros().scale();
      return new BigDecimal(number.doubleValue()).setScale(scale, JsonLogic.getInstance().getJsonLogicConfig().getRoundingMode());
  }
}
