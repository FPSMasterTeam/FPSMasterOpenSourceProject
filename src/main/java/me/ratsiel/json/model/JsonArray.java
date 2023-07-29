package me.ratsiel.json.model;

import me.ratsiel.json.abstracts.JsonValue;
import me.ratsiel.json.interfaces.IListable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The class Json array is used to store objects of type {@link JsonValue} in {@link #values}
 */
public class JsonArray extends JsonValue implements IListable<JsonValue> {


    /**
     * The Values is a list which is used by the {@link IListable}
     */
    protected final List<JsonValue> values = new ArrayList<>();

    /**
     * Instantiates a new Json array.
     */
    public JsonArray() {
    }

    /**
     * Instantiates a new Json array.
     *
     * @param key the key is the identifier of {@link JsonValue}
     */
    public JsonArray(String key) {
        super(key);
    }




    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        String space = createSpace();

        stringBuilder.append(space);
        if (getKey() != null && !getKey().isEmpty()) {
            stringBuilder.append("\"").append(getKey()).append("\"").append(" : ");
        }

        stringBuilder.append("[");
        loop((integer, jsonValue) -> {
            jsonValue.setIntend(getIntend() + 2);
            stringBuilder.append("\n");
            if(!(integer == size() - 1)) {
                stringBuilder.append(jsonValue).append(",");
            } else {
                stringBuilder.append(jsonValue);
            }
        });
        stringBuilder.append("\n").append(space).append("]");

        return stringBuilder.toString();
    }


    @Override
    public int size() {
        return values.size();
    }

    @Override
    public void add(JsonValue value) {
        values.add(value);
    }

    @Override
    public void add(int index, JsonValue value) {
        values.set(index, value);
    }

    public void addString(String value) {
        JsonString jsonString = new JsonString(value);
        this.add(jsonString);
    }

    public void addBoolean(boolean value) {
        JsonBoolean jsonBoolean = new JsonBoolean(value);
        this.add(jsonBoolean);
    }

    public void addByte(byte value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    public void addInteger(int value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    public void addShort(short value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    public void addDouble(double value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    public void addFloat(float value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    public void addLong(long value) {
        JsonNumber jsonNumber = new JsonNumber(String.valueOf(value));
        this.add(jsonNumber);
    }

    @Override
    public JsonValue get(int index, Class<JsonValue> clazz) {
        return clazz.cast(get(index));
    }

    @Override
    public JsonValue get(int index) {
        return values.get(index);
    }

    @Override
    public void loop(Consumer<JsonValue> consumer) {
        for (JsonValue value : values) {
            consumer.accept(value);
        }
    }

    @Override
    public void loop(BiConsumer<Integer, JsonValue> consumer) {
        for (int index = 0; index < values.size(); index++) {
            JsonValue jsonValue = values.get(index);
            consumer.accept(index, jsonValue);
        }
    }

}
