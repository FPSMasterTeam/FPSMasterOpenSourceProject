package me.ratsiel.json.abstracts;

/**
 * The abstract class {@link JsonHandler} with generic type {@link T} is used to serialize and deserialize an object
 *
 * @param <T> the type parameter type of object which is going to be serialized or deserialized.
 */
public abstract class JsonHandler<T> {

    /**
     * Serialize {@link JsonValue} to object of type {@link T}
     *
     * @param jsonValue the json value can be a value like something {@link me.ratsiel.json.model.JsonObject} or {@link me.ratsiel.json.model.JsonArray}
     * @return the t
     */
    public abstract T serialize(JsonValue jsonValue);

    /**
     * Deserialize {@link T} to {@link JsonValue}
     *
     * @param value the value is an object of {@link T}
     * @return the json value is the deserialized value of {@link T}
     */
    public abstract JsonValue deserialize(T value);

}
