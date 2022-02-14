package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.ValueParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

public class MathExpression implements PreEvaluatedArgumentsExpression {
  public static final MathExpression ADD = new MathExpression("+", BigDecimal::add);
  public static final MathExpression SUBTRACT = new MathExpression("-", (a, b) -> ValueParser.setScaleAndPrecision(a.subtract(b)), 2);
  public static final MathExpression MULTIPLY = new MathExpression("*", (a, b) -> ValueParser.setScaleAndPrecision(a.multiply(b)));
  public static final MathExpression DIVIDE = new MathExpression("/", (a, b) -> ValueParser.setScaleAndPrecision(a.divide(b)) , 2);
  public static final MathExpression MODULO = new MathExpression("%", (a, b) -> ValueParser.setScaleAndPrecision(a.remainder(b)), 2);
  public static final MathExpression MIN = new MathExpression("min", BigDecimal::min);
  public static final MathExpression MAX = new MathExpression("max", BigDecimal::max);

  private final String key;
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> reducer;
  private final int maxArguments;

  public MathExpression(String key, BiFunction<BigDecimal, BigDecimal, BigDecimal> reducer) {
    this(key, reducer, 0);
  }

  public MathExpression(String key, BiFunction<BigDecimal, BigDecimal, BigDecimal> reducer, int maxArguments) {
    this.key = key;
    this.reducer = reducer;
    this.maxArguments = maxArguments;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
    if (arguments.isEmpty()) {
      return null;
    }

    if (arguments.size() == 1) {
      if (key.equals("+") && arguments.get(0) instanceof String) {
        try {
          return ValueParser.parseStringToBigDecimal((String) arguments.get(0));
        }
        catch (NumberFormatException e) {
          throw new JsonLogicEvaluationException(e);
        }
      }

      if (key.equals("-") && arguments.get(0) instanceof Number) {
        return ValueParser.setScaleAndPrecision(((BigDecimal)arguments.get(0))
                .multiply(ValueParser.parseDoubleToBigDecimal(-1.0)));
      }

      if (key.equals("/")) {
        return null;
      }
    }

    // Collect all of the arguments
    BigDecimal[] values = new BigDecimal[arguments.size()];

    for (int i = 0; i < arguments.size(); i++) {
      Object value = arguments.get(i);

      if (value instanceof String) {
        try {
          values[i] = ValueParser.parseStringToBigDecimal((String) value);
        }
        catch (NumberFormatException e) {
          return null;
        }
      }
      else if (!(value instanceof Number)) {
        return null;
      }
      else {
        values[i] = ((BigDecimal) value);
      }
    }


    // Reduce the values into a single result
    BigDecimal accumulator = values[0];

    for (int i = 1; i < values.length && (i < maxArguments || maxArguments == 0); i++) {
      accumulator = reducer.apply(accumulator, values[i]);
    }

    return accumulator;
  }
}
