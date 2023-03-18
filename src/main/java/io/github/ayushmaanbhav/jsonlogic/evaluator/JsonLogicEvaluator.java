package io.github.ayushmaanbhav.jsonlogic.evaluator;

import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicArray;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicNode;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicNumber;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicOperation;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicPrimitive;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicVariable;
import io.github.ayushmaanbhav.jsonlogic.utils.ArrayLike;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import io.github.ayushmaanbhav.jsonlogic.utils.ValueParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonLogicEvaluator {
    private final Map<String, JsonLogicExpression> expressions = new HashMap<>();
    private JsonLogicConfig jsonLogicConfig;

    public JsonLogicEvaluator(Collection<JsonLogicExpression> expressions, JsonLogicConfig jsonLogicConfig) {
        for (JsonLogicExpression expression : expressions) {
            this.expressions.put(expression.key(), expression);
            this.jsonLogicConfig = jsonLogicConfig;
        }
    }

    public Object evaluate(JsonLogicNode node, Object data) throws JsonLogicEvaluationException {
        switch (node.getType()) {
            case PRIMITIVE:
                return evaluate((JsonLogicPrimitive) node);
            case VARIABLE:
                return evaluate((JsonLogicVariable) node, data);
            case ARRAY:
                return evaluate((JsonLogicArray) node, data);
            default:
                return evaluate((JsonLogicOperation) node, data);
        }
    }

    public Object evaluate(JsonLogicPrimitive<?> primitive) {
        switch (primitive.getPrimitiveType()) {
            case NUMBER:
                return ((JsonLogicNumber) primitive).getValue();

            default:
                return primitive.getValue();
        }
    }

    public Object evaluate(JsonLogicVariable variable, Object data) throws JsonLogicEvaluationException {
        Object defaultValue = evaluate(variable.getDefaultValue(), null);

        if (data == null) {
            return defaultValue;
        }

        Object key = evaluate(variable.getKey(), data);

        if (key == null) {
            return Optional.of(data)
                .map(obj -> JsonLogicEvaluator.transform(obj, this.jsonLogicConfig))
                .orElse(evaluate(variable.getDefaultValue(), null));
        }

        if (key instanceof Number) {
            int index = ((Number) key).intValue();

            if (ArrayLike.isEligible(data)) {
                ArrayLike list = new ArrayLike(data, jsonLogicConfig);

                if (index >= 0 && index < list.size()) {
                    return transform(list.get(index), this.jsonLogicConfig);
                }
            }

            return defaultValue;
        }

        // Handle the case when the key is a string, potentially referencing an infinitely-deep map: x.y.z
        if (key instanceof String) {
            String name = (String) key;

            if (name.isEmpty()) {
                return data;
            }

            String[] keys = name.split("/");
            Object result = data;

            for (String partial : keys) {
                result = evaluatePartialVariable(partial, result);

                if (result == null) {
                    return defaultValue;
                }
            }

            return result;
        }

        throw new JsonLogicEvaluationException("var first argument must be null, number, or string");
    }

    private Object evaluatePartialVariable(String key, Object data) throws JsonLogicEvaluationException {
        if (ArrayLike.isEligible(data)) {
            ArrayLike list = new ArrayLike(data, jsonLogicConfig);
            int index;

            try {
                index = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                throw new JsonLogicEvaluationException(e);
            }

            if (index < 0 || index > list.size()) {
                return null;
            }

            return transform(list.get(index), this.jsonLogicConfig);
        }

        if (data instanceof Map) {
            return transform(((Map) data).get(key), this.jsonLogicConfig);
        }

        return null;
    }

    public List<Object> evaluate(JsonLogicArray array, Object data) throws JsonLogicEvaluationException {
        List<Object> values = new ArrayList<>(array.size());

        for (JsonLogicNode element : array) {
            values.add(evaluate(element, data));
        }

        return values;
    }

    public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
        JsonLogicExpression handler = expressions.get(operation.getOperator());

        if (handler == null) {
            throw new JsonLogicEvaluationException("Undefined operation '" + operation.getOperator() + "'");
        }

        return handler.evaluate(this, operation.getArguments(), data);
    }

    public static Object transform(Object value, JsonLogicConfig jsonLogicConfig) {
        if (value instanceof Number) {
            return ValueParser.parseStringToBigDecimal(value.toString(), jsonLogicConfig);
        }

        return value;
    }

    public JsonLogicConfig getJsonLogicConfig() {
        return this.jsonLogicConfig;
    }
}
