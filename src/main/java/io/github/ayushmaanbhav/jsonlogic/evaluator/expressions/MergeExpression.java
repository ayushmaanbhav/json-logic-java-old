package io.github.ayushmaanbhav.jsonlogic.evaluator.expressions;

import io.github.ayushmaanbhav.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.ayushmaanbhav.jsonlogic.utils.ArrayLike;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MergeExpression implements PreEvaluatedArgumentsExpression {
    public static final MergeExpression INSTANCE = new MergeExpression();

    private MergeExpression() {
        // Use INSTANCE instead.
    }

    @Override
    public String key() {
        return "merge";
    }

    @Override
    public Object evaluate(List arguments, Object data, JsonLogicConfig jsonLogicConfig)
        throws JsonLogicEvaluationException {
        return ((List<Object>) arguments).stream()
            .map(obj -> ArrayLike.isEligible(obj) ? new ArrayLike(obj, jsonLogicConfig) : Collections.singleton(obj))
            .map(Collection::stream)
            .flatMap(Function.identity())
            .collect(Collectors.toList());
    }

}
