package io.github.ayushmaanbhav.jsonlogic.ast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import io.github.ayushmaanbhav.jsonlogic.utils.ValueParser;
import io.github.ayushmaanbhav.jsonlogic.utils.JsonLogicConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JsonLogicParser {
    private static final JsonParser PARSER = new JsonParser();

    private JsonLogicParser() {
        // Utility class has no public constructor.
    }

    public static JsonLogicNode parse(String json, JsonLogicConfig jsonLogicConfig) throws JsonLogicParseException {
        try {
            return parse(PARSER.parse(json), jsonLogicConfig);
        } catch (JsonSyntaxException e) {
            throw new JsonLogicParseException(e);
        }
    }

    private static JsonLogicNode parse(JsonElement root, JsonLogicConfig jsonLogicConfig)
        throws JsonLogicParseException {
        // Handle null
        if (root.isJsonNull()) {
            return JsonLogicNull.NULL;
        }

        // Handle primitives
        if (root.isJsonPrimitive()) {
            JsonPrimitive primitive = root.getAsJsonPrimitive();

            if (primitive.isString()) {
                return new JsonLogicString(primitive.getAsString());
            }

            if (primitive.isNumber()) {
                return new JsonLogicNumber(
                    ValueParser.parseStringToBigDecimal(primitive.getAsString(), jsonLogicConfig));
            }

            if (primitive.isBoolean() && primitive.getAsBoolean()) {
                return JsonLogicBoolean.TRUE;
            } else {
                return JsonLogicBoolean.FALSE;
            }
        }

        // Handle arrays
        if (root.isJsonArray()) {
            JsonArray array = root.getAsJsonArray();
            List<JsonLogicNode> elements = new ArrayList<>(array.size());

            for (JsonElement element : array) {
                elements.add(parse(element, jsonLogicConfig));
            }

            return new JsonLogicArray(elements);
        }

        // Handle objects & variables
        JsonObject object = root.getAsJsonObject();

        if (object.keySet().size() != 1) {
            throw new JsonLogicParseException(
                "objects must have exactly 1 key defined, found " + object.keySet().size());
        }

        String key = object.keySet().stream().findAny().get();
        JsonLogicNode argumentNode = parse(object.get(key), jsonLogicConfig);
        JsonLogicArray arguments;

        // Always coerce single-argument operations into a JsonLogicArray with a single element.
        if (argumentNode instanceof JsonLogicArray) {
            arguments = (JsonLogicArray) argumentNode;
        } else {
            arguments = new JsonLogicArray(Collections.singletonList(argumentNode));
        }

        // Special case for variable handling
        if ("var".equals(key)) {
            JsonLogicNode defaultValue = arguments.size() > 1 ? arguments.get(1) : JsonLogicNull.NULL;
            return new JsonLogicVariable(arguments.size() < 1 ? JsonLogicNull.NULL : arguments.get(0), defaultValue);
        }

        // Handle regular operations
        return new JsonLogicOperation(key, arguments);
    }
}
