package top.fpsmaster.core.script.api;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.utils.ui.NotificationType;
import top.fpsmaster.utils.ui.NotificationsUtils;

/**
 * @description: 添加参数
 * @author: QianXia
 * @create: 2020/11/5 14:40
 **/
public class Values {
    private final Module mod;

    public Values(Module mod) {
        this.mod = mod;
    }

    public NumberValue<Double> addNumberValue(String name, double value, double min, double max, double inc) {
        NumberValue<Double> num = new NumberValue<>(name, value, min, max, inc);
        try {
            mod.addValues(num);
        } catch (Exception e) {
            e.printStackTrace();
            // ErrorUtil.printException(e);
            NotificationsUtils.sendMessage(NotificationType.ERROR, "添加参数失败");
        }
        return num;
    }

    public BooleanValue addBooleanValue(String name, boolean state) {
        BooleanValue bool = new BooleanValue(name, state);
        try {
            mod.addValues(bool);
        } catch (Exception e) {
            e.printStackTrace();
            // ErrorUtil.printException(e);
            NotificationsUtils.sendMessage(NotificationType.ERROR, "添加参数失败");
        }
        return bool;
    }
}
