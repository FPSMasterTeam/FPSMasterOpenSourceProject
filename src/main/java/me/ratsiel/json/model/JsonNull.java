package me.ratsiel.json.model;

import me.ratsiel.json.abstracts.JsonValue;

/**
 * The class Json null store a null value.
 */
public class JsonNull extends JsonValue {

    /**
     * The Value is a final value because null is null!
     */
    protected final String value = "null";

    /**
     * Instantiates a new Json null.
     */
    public JsonNull() {
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String space = createSpace();


        stringBuilder.append(space);
        if (getKey() != null && !getKey().isEmpty()) {
            stringBuilder.append("\"").append(getKey()).append("\"").append(" : ");
        }
        stringBuilder.append("null");

        return stringBuilder.toString();
    }

    /**
     * Gets value.
     *
     * @return the value is the stored at {@link #value}
     */
    public String getValue() {
        return value;
    }
}
