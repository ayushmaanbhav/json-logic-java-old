package io.github.ayushmaanbhav.jsonlogic.ast;

public interface JsonLogicPrimitive<T> extends JsonLogicNode {
    T getValue();

    JsonLogicPrimitiveType getPrimitiveType();

    @Override
    default JsonLogicNodeType getType() {
        return JsonLogicNodeType.PRIMITIVE;
    }
}
