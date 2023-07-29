package top.fpsmaster.core.script;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.misc.EventChat;
import top.fpsmaster.event.events.impl.misc.EventKey;
import top.fpsmaster.event.events.impl.misc.EventTick;
import top.fpsmaster.event.events.impl.player.EventMotion;
import top.fpsmaster.event.events.impl.render.EventRender2D;
import top.fpsmaster.event.events.impl.render.EventRender3D;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.lang.reflect.Method;

/**
 * @description: ...
 * @author: QianXia
 * @create: 2020/11/4 19:05
 **/
public class ScriptModule extends Module {
    private final String moduleName;
    private final ModuleCategory category;
    private final Invocable invoke;

    public ScriptModule(String moduleName, ModuleCategory category, Invocable invocable) {
        super(moduleName, "Script Module", category);
        this.moduleName = moduleName;
        this.category = category;
        this.invoke = invocable;
    }

    @EventTarget
    public void EventChat(EventChat event) {
        try {
            invoke.invokeFunction("onChat", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        try {
            invoke.invokeFunction("onRender2D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        try {
            invoke.invokeFunction("onRender3D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventKey(EventKey event) {
        try {
            invoke.invokeFunction("onKey", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventMotion(EventMotion event) {
        try {
            invoke.invokeFunction("onMotion", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventRender3D(EventRender3D event) {
        try {
            invoke.invokeFunction("onRender3D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventRender2D(EventRender2D event) {
        try {
            invoke.invokeFunction("onRender2D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventTick(EventTick event) {
        try {
            invoke.invokeFunction("onTick", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @Override
    public void onEnable() {
        try {
            invoke.invokeFunction("onEnabled");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @Override
    public void onDisable() {
        try {
            invoke.invokeFunction("onDisable");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    private void unregisterMe() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String invokedMethodName = stackTrace[2].getMethodName();
            Class<?> eventClazz;
            try {
                eventClazz = Class.forName(invokedMethodName);
            } catch (ClassNotFoundException ignore) {
                // 一般来说 找不到的Method就是onDisable和onEnabled
                // 而这两个Method不在EventManager中被注册
                // 所以直接return掉
                return;
            }
            Method invokedMethod = getClass().getDeclaredMethod(invokedMethodName, eventClazz);
            EventManager.unregister(this, invokedMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
