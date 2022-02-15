package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.ast.JsonLogicNode;
import io.github.jamsesso.jsonlogic.ast.JsonLogicParser;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicExpression;
import io.github.jamsesso.jsonlogic.evaluator.expressions.*;
import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class JsonLogic {
  private final List<JsonLogicExpression> expressions;
  private final Map<String, JsonLogicNode> parseCache;
  private JsonLogicEvaluator evaluator;
  private final JsonLogicConfig jsonLogicConfig;
  private static JsonLogic jsonLogic;

  public static JsonLogic getInstance(){
      if(jsonLogic==null){
        JsonLogicConfig jsonLogicConfig=new JsonLogicConfig(2, RoundingMode.HALF_UP);
        jsonLogic=new JsonLogic(jsonLogicConfig);
      }
      return jsonLogic;
  }

  public static JsonLogic getInstance(JsonLogicConfig jsonLogicConfig){
    if(jsonLogic==null){
      jsonLogic=new JsonLogic(jsonLogicConfig);
    }
    return jsonLogic;
  }
  public JsonLogic(JsonLogicConfig jsonLogicConfig) {
    this.expressions = new ArrayList<>();
    this.parseCache = new ConcurrentHashMap<>();
    this.jsonLogicConfig=jsonLogicConfig;
    // Add default operations
    addOperation(MathExpression.ADD.withConfig(jsonLogicConfig));
    addOperation(MathExpression.SUBTRACT.withConfig(jsonLogicConfig));
    addOperation(MathExpression.MULTIPLY.withConfig(jsonLogicConfig));
    addOperation(MathExpression.DIVIDE.withConfig(jsonLogicConfig));
    addOperation(MathExpression.MODULO.withConfig(jsonLogicConfig));
    addOperation(MathExpression.MIN.withConfig(jsonLogicConfig));
    addOperation(MathExpression.MAX.withConfig(jsonLogicConfig));
    addOperation(NumericComparisonExpression.GT.withConfig(jsonLogicConfig));
    addOperation(NumericComparisonExpression.GTE.withConfig(jsonLogicConfig));
    addOperation(NumericComparisonExpression.LT.withConfig(jsonLogicConfig));
    addOperation(NumericComparisonExpression.LTE.withConfig(jsonLogicConfig));
    addOperation(IfExpression.IF);
    addOperation(IfExpression.TERNARY);
    addOperation(EqualityExpression.INSTANCE.withConfig(jsonLogicConfig));
    addOperation(InequalityExpression.INSTANCE);
    addOperation(StrictEqualityExpression.INSTANCE);
    addOperation(StrictInequalityExpression.INSTANCE);
    addOperation(NotExpression.SINGLE);
    addOperation(NotExpression.DOUBLE);
    addOperation(LogicExpression.AND);
    addOperation(LogicExpression.OR);
    addOperation(LogExpression.STDOUT);
    addOperation(MapExpression.INSTANCE);
    addOperation(FilterExpression.INSTANCE);
    addOperation(ReduceExpression.INSTANCE);
    addOperation(AllExpression.INSTANCE);
    addOperation(ArrayHasExpression.SOME);
    addOperation(ArrayHasExpression.NONE);
    addOperation(MergeExpression.INSTANCE);
    addOperation(InExpression.INSTANCE);
    addOperation(ConcatenateExpression.INSTANCE);
    addOperation(SubstringExpression.INSTANCE);
    addOperation(MissingExpression.ALL);
    addOperation(MissingExpression.SOME);
  }

  public JsonLogicConfig getJsonLogicConfig(){
    return jsonLogicConfig;
  }

  public JsonLogic addOperation(String name, Function<Object[], Object> function) {
    return addOperation(new PreEvaluatedArgumentsExpression() {
      @Override
      public Object evaluate(List arguments, Object data) {
        return function.apply(arguments.toArray());
      }

      @Override
      public String key() {
        return name;
      }
    });
  }

  public JsonLogic addOperation(JsonLogicExpression expression) {
    expressions.add(expression);
    evaluator = null;

    return this;
  }

  public Object apply(String json, Object data) throws JsonLogicException {
    if (!parseCache.containsKey(json)) {
      parseCache.put(json, JsonLogicParser.parse(json,this.jsonLogicConfig));
    }

    if (evaluator == null) {
      evaluator = new JsonLogicEvaluator(expressions);
    }

    return evaluator.evaluate(parseCache.get(json), data);
  }

  public static boolean truthy(Object value) {
    if (value == null) {
      return false;
    }

    if (value instanceof Boolean) {
      return (boolean) value;
    }

    if (value instanceof Number) {
      if (value instanceof Double) {
        Double d = (Double) value;

        if (d.isNaN()) {
          return false;
        }
        else if (d.isInfinite()) {
          return true;
        }
      }

      if (value instanceof Float) {
        Float f = (Float) value;

        if (f.isNaN()) {
          return false;
        }
        else if (f.isInfinite()) {
          return true;
        }
      }
      if(value instanceof BigDecimal){
        BigDecimal bigDecimal=(BigDecimal)value;
        return bigDecimal.compareTo(BigDecimal.valueOf(0.0))!=0;
      }

      return ((Number) value).doubleValue() != 0.0;
    }

    if (value instanceof String) {
      return !((String) value).isEmpty();
    }

    if (value instanceof Collection) {
      return !((Collection) value).isEmpty();
    }

    if (value.getClass().isArray()) {
      return Array.getLength(value) > 0;
    }

    return true;
  }
}
