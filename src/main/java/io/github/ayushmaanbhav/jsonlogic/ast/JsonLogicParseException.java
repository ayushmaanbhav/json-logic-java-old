package io.github.ayushmaanbhav.jsonlogic.ast;

import io.github.ayushmaanbhav.jsonlogic.JsonLogicException;

public class JsonLogicParseException extends JsonLogicException {
    public JsonLogicParseException(String msg) {
        super(msg);
    }

    public JsonLogicParseException(Throwable cause) {
        super(cause);
    }

    public JsonLogicParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
