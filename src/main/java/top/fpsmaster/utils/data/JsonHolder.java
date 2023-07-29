package top.fpsmaster.utils.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JsonHolder {
    public static ThreadLocal printFormattingException = ThreadLocal.withInitial(() -> true);
    private JsonObject object;

    public JsonHolder(JsonObject object) {
        this.object = object;
    }

    public JsonHolder(String raw) {
        if (raw == null) {
            this.object = new JsonObject();
        } else {
            try {
                this.object = (new JsonParser()).parse(raw).getAsJsonObject();
            } catch (Exception var3) {
                this.object = new JsonObject();
                if ((Boolean) printFormattingException.get()) {
                    var3.printStackTrace();
                }
            }
        }

    }

    public JsonHolder() {
        this(new JsonObject());
    }

    public void ensureJsonHolder(String key) {
        if (!this.has(key)) {
            this.put(key, new JsonHolder());
        }

    }

    public void ensureJsonArray(String key) {
        if (!this.has(key)) {
            this.put(key, new JsonArray());
        }

    }

    public JsonHolder optOrCreateJsonHolder(String key) {
        this.ensureJsonHolder(key);
        return this.optJSONObject(key);
    }

    public JsonArray optOrCreateJsonArray(String key) {
        this.ensureJsonArray(key);
        return this.optJSONArray(key);
    }

    public String toString() {
        return this.object != null ? this.object.toString() : "{}";
    }

    public JsonHolder put(String key, boolean value) {
        this.object.addProperty(key, value);
        return this;
    }

    public void mergeNotOverride(JsonHolder merge) {
        this.merge(merge, false);
    }

    public void mergeOverride(JsonHolder merge) {
        this.merge(merge, true);
    }

    public void merge(JsonHolder merge, boolean override) {
        JsonObject object = merge.getObject();
        merge.getKeys().stream().filter((s) -> override || !this.has((String) s)).forEach((s) -> this.put((String) s, object.get((String) s)));
    }

    private JsonHolder put(String s, JsonElement element) {
        this.object.add(s, element);
        return this;
    }

    public JsonHolder put(String key, String value) {
        this.object.addProperty(key, value);
        return this;
    }

    public JsonHolder put(String key, int value) {
        this.object.addProperty(key, value);
        return this;
    }

    public JsonHolder put(String key, double value) {
        this.object.addProperty(key, value);
        return this;
    }

    public JsonHolder put(String key, long value) {
        this.object.addProperty(key, value);
        return this;
    }

    private JsonHolder defaultOptJSONObject(String key, JsonObject fallBack) {
        try {
            return new JsonHolder(this.object.get(key).getAsJsonObject());
        } catch (Exception var4) {
            return new JsonHolder(fallBack);
        }
    }

    public JsonArray defaultOptJSONArray(String key, JsonArray fallback) {
        try {
            return this.object.get(key).getAsJsonArray();
        } catch (Exception var4) {
            return fallback;
        }
    }

    public JsonArray optJSONArray(String key) {
        return this.defaultOptJSONArray(key, new JsonArray());
    }

    public boolean has(String key) {
        return this.object.has(key);
    }

    public long optLong(String key, long fallback) {
        try {
            JsonElement jsonElement = this.object.get(key);
            if (jsonElement != null) {
                return jsonElement.getAsLong();
            }
        } catch (Exception var5) {
        }

        return fallback;
    }

    public long optLong(String key) {
        return this.optLong(key, 0L);
    }

    public boolean optBoolean(String key, boolean fallback) {
        try {
            JsonElement jsonElement = this.object.get(key);
            if (jsonElement != null) {
                return jsonElement.getAsBoolean();
            }
        } catch (Exception var4) {
        }

        return fallback;
    }

    public boolean optBoolean(String key) {
        return this.optBoolean(key, false);
    }

    public JsonObject optActualJSONObject(String key) {
        try {
            return this.object.get(key).getAsJsonObject();
        } catch (Exception var3) {
            return new JsonObject();
        }
    }

    public JsonHolder optJSONObject(String key) {
        return this.defaultOptJSONObject(key, new JsonObject());
    }

    public int optInt(String key, int fallBack) {
        try {
            return this.object.get(key).getAsInt();
        } catch (Exception var4) {
            return fallBack;
        }
    }

    public int optInt(String key) {
        return this.optInt(key, 0);
    }

    public String defaultOptString(String key, String fallBack) {
        try {
            JsonElement jsonElement = this.object.get(key);
            if (jsonElement != null) {
                return jsonElement.getAsString();
            }
        } catch (Exception var4) {
        }

        return fallBack;
    }

    public String optString(String key) {
        return this.defaultOptString(key, "");
    }

    public double optDouble(String key, double fallBack) {
        try {
            JsonElement jsonElement = this.object.get(key);
            if (jsonElement != null) {
                return jsonElement.getAsDouble();
            }
        } catch (Exception var5) {
        }

        return fallBack;
    }

    public List getKeys() {
        List tmp = new ArrayList();
        this.object.entrySet().forEach((e) -> {
            tmp.add(e.getKey());
        });
        return tmp;
    }

    public double optDouble(String key) {
        return this.optDouble(key, 0.0D);
    }

    public int getSize() {
        return this.object.entrySet().size();
    }

    public JsonObject getObject() {
        return this.object;
    }

    public boolean isNull(String key) {
        return this.object.has(key) && this.object.get(key).isJsonNull();
    }

    public JsonHolder put(String values, JsonHolder values1) {
        return this.put(values, values1.getObject());
    }

    public JsonHolder put(String values, JsonObject object) {
        this.object.add(values, object);
        return this;
    }

    public JsonHolder put(String key, JsonArray jsonElements) {
        this.object.add(key, jsonElements);
        return this;
    }

    public void remove(String header) {
        this.object.remove(header);
    }

    public JsonElement removeAndGet(String header) {
        return this.object.remove(header);
    }
}
