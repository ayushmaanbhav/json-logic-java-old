package io.github.ayushmaanbhav.jsonlogic.evaluator.expressions;

import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicArray;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicExpression;
import io.github.ayushmaanbhav.jsonlogic.utils.ArrayLike;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;

import java.util.List;

public interface PreEvaluatedArgumentsExpression extends JsonLogicExpression {
    Object evaluate(List arguments, Object data, JsonLogicConfig jsonLogicConfig) throws JsonLogicEvaluationException;

    @Override
    default Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException {
        List<Object> values = evaluator.evaluate(arguments, data);

        if (values.size() == 1 && ArrayLike.isEligible(values.get(0))) {
            values = new ArrayLike(values.get(0), evaluator.getJsonLogicConfig());
        }

        return evaluate(values, data, evaluator.getJsonLogicConfig());
    }
}
