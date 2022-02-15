package io.github.jamsesso.jsonlogic.evaluator.expressions;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;
import io.github.jamsesso.jsonlogic.utils.ValueParser;
import org.apache.commons.lang3.function.TriFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MathExpression implements PreEvaluatedArgumentsExpression {
  public static final MathExpression ADD = new MathExpression("+", (a,b,config) -> a.add(b));
  public static final MathExpression SUBTRACT = new MathExpression("-", (a, b,config) -> a.subtract(b), 2);
  public static final MathExpression MULTIPLY = new MathExpression("*", (a, b,config) -> ValueParser.setScaleAndPrecision(a.multiply(b),config));
  public static final MathExpression DIVIDE = new MathExpression("/", (a, b,config) -> a.divide(b,config.getScale(),config.getRoundingMode()) , 2);
  public static final MathExpression MODULO = new MathExpression("%", (a, b,config) -> a.remainder(b), 2);
  public static final MathExpression MIN = new MathExpression("min", (a,b,config) -> a.min(b));
  public static final MathExpression MAX = new MathExpression("max", (a,b,config) -> a.max(b));

  private final String key;
  private final TriFunction<BigDecimal, BigDecimal,JsonLogicConfig, BigDecimal> reducer;
  private final int maxArguments;
  private final JsonLogicConfig jsonLogicConfig;

  public MathExpression(String key, TriFunction<BigDecimal, BigDecimal,JsonLogicConfig, BigDecimal> reducer) {
    this(key, reducer, 0);
  }

  public MathExpression(String key, TriFunction<BigDecimal, BigDecimal, JsonLogicConfig,BigDecimal> reducer, int maxArguments) {
    this.key = key;
    this.reducer = reducer;
    this.maxArguments = maxArguments;
    this.jsonLogicConfig=new JsonLogicConfig(3, RoundingMode.HALF_UP);
  }
  public MathExpression(String key, TriFunction<BigDecimal, BigDecimal, JsonLogicConfig,BigDecimal> reducer, int maxArguments,JsonLogicConfig jsonLogicConfig){
    this.key = key;
    this.reducer = reducer;
    this.maxArguments = maxArguments;
    this.jsonLogicConfig=jsonLogicConfig;
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
          return ValueParser.parseStringToBigDecimal((String) arguments.get(0),this.jsonLogicConfig);
        }
        catch (NumberFormatException e) {
          throw new JsonLogicEvaluationException(e);
        }
      }

      if (key.equals("-") && arguments.get(0) instanceof Number) {
        return ((BigDecimal)arguments.get(0)).negate();
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
          values[i] = ValueParser.parseStringToBigDecimal((String) value,this.jsonLogicConfig);
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
      accumulator = reducer.apply(accumulator, values[i],this.jsonLogicConfig);
    }

    return accumulator;
  }

  public MathExpression withConfig(JsonLogicConfig jsonLogicConfig){
    return new MathExpression(this.key,this.reducer,this.maxArguments,jsonLogicConfig);
  }
}
