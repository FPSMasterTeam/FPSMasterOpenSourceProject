package top.fpsmaster.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.core.values.Value;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.core.values.values.ModeValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.gui.notification.Notification;
import top.fpsmaster.modules.render.ClickGui;
import top.fpsmaster.modules.render.NotificationModule;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Super
 */
public class Module {
    public String name;
    public String desc;
    public boolean stage;
    public int key;
    public ModuleCategory type;
    public float x, y, width, height, scale = 1;
    public Minecraft mc = Minecraft.getMinecraft();
    public ArrayList<Value<?>> values = new ArrayList<>();
    public boolean canBeEnabled;

    public ArrayList<Value<?>> getValues() {
        ArrayList<Value<?>> validValues = new ArrayList<>();
        values.forEach(value -> {
            if (!value.isHidden()) {
                validValues.add(value);
            }
        });
        return validValues;
    }

    public void addValues(Value<?>... vs) {
        values.addAll(Arrays.asList(vs));
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public Module(String name, String desc, ModuleCategory type) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.canBeEnabled = true;
    }

    public void onGui() {

    }

    public void onEnable() {
    }

    public void onDisable() {

    }

    public void setStage(boolean stage) {
        if (!this.canBeEnabled) return;
        this.stage = stage;
        if (stage) {
            onEnable();
            EventManager.register(this);
            if (!(this instanceof ClickGui) && FPSMaster.INSTANCE.notificationsManager != null && NotificationModule.showModuleToggle.getValue())
                FPSMaster.INSTANCE.notificationsManager.add(new Notification(I18NUtils.getString(this.name), Notification.Type.Success));
        } else {
            EventManager.unregister(this);
            onDisable();
            if (!(this instanceof ClickGui) && FPSMaster.INSTANCE.notificationsManager != null && NotificationModule.showModuleToggle.getValue())
                FPSMaster.INSTANCE.notificationsManager.add(new Notification(I18NUtils.getString(this.name), Notification.Type.Error));
        }
    }

    public void toggle() {
        setStage(!this.stage);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return 1f;
    }

    public void onRight() {

    }

    public void setValues(ArrayList<Value<?>> values) {
        for (Value<?> v : values) {
            for (Value<?> thisValue : this.values) {
                if (thisValue.name.equals(v.name) && thisValue.getClass().equals(v.getClass())) {
                    try {
                        Field field = thisValue.getClass().getSuperclass().getDeclaredField("value");
                        field.setAccessible(true);
                        field.set(thisValue, v.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fromJson(@Nullable JsonObject je) {
        if (je != null) {
            if (!(this instanceof ClickGui)) {
                setStage(je.get("stage").getAsBoolean());
            }
            setScale(je.get("scale").getAsFloat());
            setX(je.get("x").getAsFloat());
            setY(je.get("y").getAsFloat());
            setKey(je.get("key").getAsInt());
            for (Value<?> v : values) {
                try {
                    if (v instanceof BooleanValue) {
                        JsonPrimitive value = je.getAsJsonPrimitive(v.name);
                        ((BooleanValue) v).setValue(value.getAsBoolean());
                    } else if (v instanceof NumberValue) {
                        JsonPrimitive value = je.getAsJsonPrimitive(v.name);
                        ((NumberValue) v).setValue(value.getAsNumber());
                    } else if (v instanceof ModeValue) {
                        JsonPrimitive value = je.getAsJsonPrimitive(v.name);
                        ((ModeValue) v).setCurrent(value.getAsString());
                    } else if (v instanceof ColorValue) {
                        JsonObject value = je.getAsJsonObject(v.name);
                        Color color = new Color(value.get("red").getAsInt(), value.get("green").getAsInt(), value.get("blue").getAsInt(), value.get("alpha").getAsInt());
                        ((ColorValue) v).setValue(color);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(v.name);
                }
            }
        }

    }
}
