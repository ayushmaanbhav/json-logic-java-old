package io.github.ayushmaanbhav.jsonlogic.evaluator;

import io.github.ayushmaanbhav.jsonlogic.ast.JsonLogicArray;

public interface JsonLogicExpression {
    String key();

    Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException;
}
