package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.JsonLogicConfig;

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
    public Object evaluate(List arguments, Object data, JsonLogicConfig jsonLogicConfig)
        throws JsonLogicEvaluationException {
        return arguments.stream()
            .map(obj -> {
                if (obj instanceof BigDecimal) {
                    return parseBigDecimal((BigDecimal) obj);
                }
                return obj;
            })
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private Object parseBigDecimal(BigDecimal number) {
        int scale = number.stripTrailingZeros().scale();
        return new BigDecimal(number.toString()).setScale(scale).toPlainString();
    }
}
