package io.github.ayushmaanbhav.jsonlogic.evaluator.expressions;

import io.github.ayushmaanbhav.jsonlogic.JsonLogic;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;
import io.github.ayushmaanbhav.jsonlogic.utils.ValueParser;

import java.math.BigDecimal;
import java.util.List;

public class EqualityExpression implements PreEvaluatedArgumentsExpression {
    public static final EqualityExpression INSTANCE = new EqualityExpression();

    private EqualityExpression() {
        // Only one instance can be constructed. Use EqualityExpression.INSTANCE
    }

    @Override
    public String key() {
        return "==";
    }

    @Override
    public Object evaluate(List arguments, Object data, JsonLogicConfig jsonLogicConfig)
        throws JsonLogicEvaluationException {
        if (arguments.size() != 2) {
            throw new JsonLogicEvaluationException("equality expressions expect exactly 2 arguments");
        }

        Object left = arguments.get(0);
        Object right = arguments.get(1);

        // Use the loose equality matrix
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Equality_comparisons_and_sameness#Loose_equality_using
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        // Check numeric loose equality
        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            return ((BigDecimal) left).compareTo((BigDecimal) right) == 0;
        }

        if (left instanceof BigDecimal && right instanceof String) {
            return compareNumberToString((BigDecimal) left, (String) right, jsonLogicConfig);
        }

        if (left instanceof BigDecimal && right instanceof Boolean) {
            return compareNumberToBoolean((BigDecimal) left, (Boolean) right);
        }

        // Check string loose equality
        if (left instanceof String && right instanceof String) {
            return left.equals(right);
        }

        if (left instanceof String && right instanceof Number) {
            return compareNumberToString((BigDecimal) right, (String) left, jsonLogicConfig);
        }

        if (left instanceof String && right instanceof Boolean) {
            return compareStringToBoolean((String) left, (Boolean) right);
        }

        // Check boolean loose equality
        if (left instanceof Boolean && right instanceof Boolean) {
            return ((Boolean) left).booleanValue() == ((Boolean) right).booleanValue();
        }

        if (left instanceof Boolean && right instanceof Number) {
            return compareNumberToBoolean((BigDecimal) right, (Boolean) left);
        }

        if (left instanceof Boolean && right instanceof String) {
            return compareStringToBoolean((String) right, (Boolean) left);
        }

        // Check non-truthy values
        return !JsonLogic.truthy(left) && !JsonLogic.truthy(right);

    }

    private boolean compareNumberToString(BigDecimal left, String right, JsonLogicConfig jsonLogicConfig) {
        try {
            if (right.trim().isEmpty()) {
                right = "0";
            }

            return ValueParser.parseStringToBigDecimal(right, jsonLogicConfig).compareTo(left) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean compareNumberToBoolean(BigDecimal left, Boolean right) {
        if (right) {
            return left.compareTo(BigDecimal.ONE) == 0;
        }

        return left.compareTo(BigDecimal.ZERO) == 0;
    }

    private boolean compareStringToBoolean(String left, Boolean right) {
        return JsonLogic.truthy(left) == right;
    }

}
