package top.fpsmaster.utils.data;

import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BetterJsonObject {
    private final Gson prettyPrinter = (new GsonBuilder()).setPrettyPrinting().create();
    private JsonObject data;

    public BetterJsonObject() {
        this.data = new JsonObject();
    }

    public BetterJsonObject(String jsonIn) {
        if (jsonIn != null && !jsonIn.isEmpty()) {
            try {
                this.data = (new JsonParser()).parse(jsonIn).getAsJsonObject();
            } catch (JsonIOException | JsonSyntaxException var3) {
                var3.printStackTrace();
            }

        } else {
            this.data = new JsonObject();
        }
    }

    public BetterJsonObject(JsonObject objectIn) {
        this.data = objectIn != null ? objectIn : new JsonObject();
    }

    public String optString(String key) {
        return this.optString(key, "");
    }

    public String optString(String key, String value) {
        if (key != null && !key.isEmpty() && this.has(key)) {
            JsonPrimitive primitive = this.asPrimitive(this.get(key));
            return primitive != null && primitive.isString() ? primitive.getAsString() : value;
        } else {
            return value;
        }
    }

    public int optInt(String key) {
        return this.optInt(key, 0);
    }

    public int optInt(String key, int value) {
        if (key != null && !key.isEmpty() && this.has(key)) {
            JsonPrimitive primitive = this.asPrimitive(this.get(key));

            try {
                if (primitive != null && primitive.isNumber()) {
                    return primitive.getAsInt();
                }
            } catch (NumberFormatException var5) {
            }

            return value;
        } else {
            return value;
        }
    }

    public double optDouble(String key) {
        return this.optDouble(key, 0.0D);
    }

    public double optDouble(String key, double value) {
        if (key != null && !key.isEmpty() && this.has(key)) {
            JsonPrimitive primitive = this.asPrimitive(this.get(key));

            try {
                if (primitive != null && primitive.isNumber()) {
                    return primitive.getAsDouble();
                }
            } catch (NumberFormatException var6) {
            }

            return value;
        } else {
            return value;
        }
    }

    public boolean optBoolean(String key) {
        return this.optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean value) {
        if (key != null && !key.isEmpty() && this.has(key)) {
            JsonPrimitive primitive = this.asPrimitive(this.get(key));
            return primitive != null && primitive.isBoolean() ? primitive.getAsBoolean() : value;
        } else {
            return value;
        }
    }

    public boolean has(String key) {
        return this.data.has(key);
    }

    public JsonElement get(String key) {
        return this.data.get(key);
    }

    public JsonObject getData() {
        return this.data;
    }

    public BetterJsonObject addProperty(String key, String value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }

        return this;
    }

    public BetterJsonObject addProperty(String key, Number value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }

        return this;
    }

    public BetterJsonObject addProperty(String key, Boolean value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }

        return this;
    }

    public BetterJsonObject add(String key, BetterJsonObject object) {
        if (key != null) {
            this.data.add(key, object.data);
        }

        return this;
    }

    public void writeToFile(File file) {
        if (file != null && (!file.exists() || !file.isDirectory())) {
            try {
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }

                    file.createNewFile();
                }

                FileWriter writer = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(this.toPrettyString());
                bufferedWriter.close();
                writer.close();
            } catch (IOException var4) {
                var4.printStackTrace();
            }

        }
    }

    private JsonPrimitive asPrimitive(JsonElement element) {
        return element instanceof JsonPrimitive ? (JsonPrimitive) element : null;
    }

    public String toString() {
        return this.data.toString();
    }

    public String toPrettyString() {
        return this.prettyPrinter.toJson(this.data);
    }
}
