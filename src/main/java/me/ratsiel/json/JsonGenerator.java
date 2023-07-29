package me.ratsiel.json;

import me.ratsiel.json.abstracts.JsonHandler;
import me.ratsiel.json.abstracts.JsonValue;
import me.ratsiel.json.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class Json generator is used to parse json to a class or object to json.
 */
public class JsonGenerator {

    /**
     * The Handlers is {@link Map} with key {@link Class<?>} and {@link JsonHandler<?>}
     */
    protected final HashMap<Class<?>, JsonHandler<?>> handlers = new HashMap<>();

    /**
     * To json value.
     * Parse object of type {@link T} to {@link JsonValue}
     *
     * @param <T>   the type parameter used to determine value
     * @param value the value used to parse
     * @return the json value returns raw type of {@link JsonValue}
     */
    public <T> JsonValue toJson(T value) {
        JsonValue jsonValue;
        if (value == null) {
            jsonValue = new JsonNull();
            return jsonValue;
        }

        JsonHandler jsonHandler = getHandler(value.getClass());

        if (jsonHandler != null) {
            return jsonHandler.deserialize(value);
        }

        Class<?> clazz = value.getClass();

        if (String.class.isAssignableFrom(clazz)) {
            jsonValue = new JsonString();
            ((JsonString) jsonValue).setValue((String) value);
            return jsonValue;
        } else if (Enum.class.isAssignableFrom(clazz)) {
            jsonValue = new JsonString();
            ((JsonString) jsonValue).setValue(((Enum) value).name());
            return jsonValue;
        } else if (Number.class.isAssignableFrom(clazz)) {
            jsonValue = new JsonNumber();
            ((JsonNumber) jsonValue).setValue(value.toString());
            return jsonValue;
        } else if (Boolean.class.isAssignableFrom(clazz)) {
            jsonValue = new JsonBoolean();
            ((JsonBoolean) jsonValue).setValue((Boolean) value);
            return jsonValue;
        } else if (List.class.isAssignableFrom(clazz)) {
            JsonArray jsonArray = new JsonArray();
            List<?> list = (List<?>) value;
            for (Object listValue : list) {
                jsonArray.add(toJson(listValue));
            }
            return jsonArray;
        } else if (Map.class.isAssignableFrom(clazz)) {
            JsonObject jsonObject = new JsonObject();
            Map<?, ?> map = (Map<?, ?>) value;

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object mapValue = entry.getValue();
                if (key == null) {
                    jsonObject.add("null", toJson(mapValue));
                    continue;
                }

                Class<?> keyClazz = key.getClass();
                if (String.class.isAssignableFrom(keyClazz)
                        || Number.class.isAssignableFrom(keyClazz)
                        || Boolean.class.isAssignableFrom(keyClazz)) {
                    jsonObject.add(String.valueOf(key), toJson(mapValue));
                } else if (Enum.class.isAssignableFrom(keyClazz)) {
                    jsonObject.add(((Enum) key).name(), toJson(mapValue));
                }

            }
            return jsonObject;
        } else {
            JsonObject jsonObject = new JsonObject();

            if (hasSuperclass(clazz)) {
                for (Field declaredField : clazz.getSuperclass().getDeclaredFields()) {
                    populateFields(value, jsonObject, declaredField);
                }
            }

            for (Field declaredField : clazz.getDeclaredFields()) {
                populateFields(value, jsonObject, declaredField);
            }

            jsonValue = jsonObject;
        }


        return jsonValue;
    }

    private <T> void populateFields(T value, JsonObject jsonObject, Field declaredField) {
        if(declaredField.getModifiers() == Modifier.STATIC) return;

        declaredField.setAccessible(true);
        try {
            JsonValue transformedValue = toJson(declaredField.get(value));
            transformedValue.setKey(declaredField.getName());

            jsonObject.add(transformedValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * From json to list.
     * Convert {@link JsonValue} to {@link T} and cast with {@link Class<K>}
     *
     * @param <T>       the type parameter determines object type
     * @param <K>       the type parameter is the class that is used t ocast
     * @param jsonValue the json value is used to convert
     * @param listClazz the list clazz is used to cast {@link T}
     * @param clazz     the clazz is used to determine object in {@link List}
     * @return T is a list with cast {@link Class<T>}
     */
    public <T extends List<K>, K> T fromJson(JsonValue jsonValue, Class<T> listClazz, Class<K> clazz) {
        return listClazz.cast(fromJson(null, jsonValue, clazz));
    }

    /**
     * From json with cast.
     * Convert {@link JsonValue} to {@link T}
     *
     * @param <T>       the type parameter determines type of return object
     * @param jsonValue the json value is used to convert to {@link T}
     * @param clazz     the clazz is used to cast {@link T}
     * @return {@link T} cast with {@link Class<T>}
     */
    public <T> T fromJsonCast(JsonValue jsonValue, Class<T> clazz) {
        return clazz.cast(fromJson(null, jsonValue, clazz));
    }

    /**
     * From json.
     * Convert {@link JsonValue} to raw object
     *
     * @param <T>       the type parameter determines type of return object
     * @param jsonValue the json value is used to convert to {@link Object}
     * @param clazz     the clazz is used create object
     * @return the object
     */
    public <T> Object fromJson(JsonValue jsonValue, Class<T> clazz) {
        return fromJson(null, jsonValue, clazz);
    }

    /**
     * From json.
     * Convert {@link JsonValue} to raw object
     *
     * @param <T>       the type parameter determines type of return object
     * @param key       the key is used to get a value out of {@link JsonObject}
     * @param jsonValue the json value is used to convert to {@link Object}
     * @param clazz     the clazz is used create object
     * @return the object
     */
    public <T> Object fromJson(String key, JsonValue jsonValue, Class<T> clazz) {
        JsonHandler jsonHandler = getHandler(clazz);

        if (jsonValue instanceof JsonString) {
            if (jsonHandler != null) {
                return jsonHandler.serialize(jsonValue);
            }

            return clazz.cast(((JsonString) jsonValue).getValue());
        } else if (jsonValue instanceof JsonNumber) {

            if (jsonHandler != null) {
                return jsonHandler.serialize(jsonValue);
            }

            return ((JsonNumber) jsonValue).getNumber(clazz);
        } else if (jsonValue instanceof JsonBoolean) {

            if (jsonHandler != null) {
                return jsonHandler.serialize(jsonValue);
            }

            return clazz.cast(((JsonBoolean) jsonValue).isValue());
        } else if (jsonValue instanceof JsonNull) {

            if (jsonHandler != null) {
                return jsonHandler.serialize(jsonValue);
            }

            return null;
        } else if (jsonValue instanceof JsonObject) {
            if (key != null) {
                return fromJson(((JsonObject) jsonValue).get(key), clazz);
            } else {
                try {
                    T object = clazz.newInstance();

                    if (jsonHandler != null) {
                        return jsonHandler.serialize(jsonValue);
                    }

                    Class<?> objectClazz = object.getClass();

                    if (hasSuperclass(objectClazz)) {
                        for (Field declaredField : objectClazz.getSuperclass().getDeclaredFields()) {
                            declaredField.setAccessible(true);
                            declaredField.set(object, fromJson(declaredField.getName(), jsonValue, declaredField.getType()));
                        }
                    }

                    for (Field declaredField : objectClazz.getDeclaredFields()) {
                        declaredField.setAccessible(true);
                        declaredField.set(object, fromJson(declaredField.getName(), jsonValue, declaredField.getType()));
                    }

                    return object;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        } else if (jsonValue instanceof JsonArray) {
            List list = new ArrayList();
            ((JsonArray) jsonValue).loop((integer, listValue) -> {
                list.add(fromJson(listValue, clazz));
            });
            return list;
        }


        return null;
    }

    /**
     * Has superclass boolean.
     * Checks if given class has a superclass
     *
     * @param <T>   the type parameter determines type of class used in class
     * @param clazz the clazz is used to check
     * @return the boolean true or false
     */
    public <T> boolean hasSuperclass(Class<T> clazz) {
        return clazz.getSuperclass() != null;
    }

    /**
     * Register handler.
     * Register a {@link JsonHandler<?>} by {@link Class<?>}
     *
     * @param clazz       the clazz used to put {@link Class<?>} and {@link JsonHandler<?>} in {@link #handlers}
     * @param jsonHandler the json handler is used to insert
     */
    public void registerHandler(Class<?> clazz, JsonHandler<?> jsonHandler) {
        if (!isRegistered(clazz)) {
            handlers.put(clazz, jsonHandler);
        }
    }

    /**
     * Unregister handler.
     * Unregister a {@link JsonHandler} by {@link Class<?>}
     *
     * @param clazz the clazz used to remove {@link JsonHandler} from {@link #handlers}
     */
    public void unregisterHandler(Class<?> clazz) {
        if (isRegistered(clazz)) {
            handlers.remove(clazz);
        }
    }

    /**
     * Gets handler from {@link #handlers}.
     *
     * @param clazz the clazz used to get {@link JsonHandler<?>} from {@link #handlers}
     * @return the handler null or {@link JsonHandler<?>}
     */
    public JsonHandler<?> getHandler(Class<?> clazz) {
        if (!isRegistered(clazz)) return null;

        return handlers.get(clazz);
    }

    /**
     * Is registered boolean.
     * Check if {@link JsonHandler<?>} is registered in {@link #handlers}
     *
     * @param clazz the clazz used to get {@link JsonHandler<?>}
     * @return the boolean true or false
     */
    public boolean isRegistered(Class<?> clazz) {
        return handlers.containsKey(clazz);
    }

}
