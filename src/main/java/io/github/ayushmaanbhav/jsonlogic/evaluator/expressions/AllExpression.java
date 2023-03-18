package io.github.ayushmaanbhav.jsonlogic.evaluator.expressions;

import io.github.ayushmaanbhav.jsonlogic.JsonLogic;
import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicArray;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicExpression;
import io.github.ayushmaanbhav.jsonlogic.utils.ArrayLike;

public class AllExpression implements JsonLogicExpression {
    public static final AllExpression INSTANCE = new AllExpression();

    private AllExpression() {
        // Use INSTANCE instead.
    }

    @Override
    public String key() {
        return "all";
    }

    @Override
    public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException {
        if (arguments.size() != 2) {
            throw new JsonLogicEvaluationException("all expects exactly 2 arguments");
        }

        Object maybeArray = evaluator.evaluate(arguments.get(0), data);

        if (!ArrayLike.isEligible(maybeArray)) {
            throw new JsonLogicEvaluationException("first argument to all must be a valid array");
        }

        ArrayLike array = new ArrayLike(maybeArray, evaluator.getJsonLogicConfig());

        if (array.size() < 1) {
            return false;
        }

        for (Object item : array) {
            if (!JsonLogic.truthy(evaluator.evaluate(arguments.get(1), item))) {
                return false;
            }
        }

        return true;
    }

}
