package top.fpsmaster.utils.math;

import com.google.common.collect.Lists;
import top.fpsmaster.utils.Utils;

import java.util.List;

public class CpsUtil {
    private final List<MouseButton> leftCounter = Lists.newArrayList();
    private final List<MouseButton> rightCounter = Lists.newArrayList();

    public int getLeftCps() {
        Utils.cpsUtil.update();
        return this.leftCounter.size();
    }

    public int getRightCps() {
        Utils.cpsUtil.update();
        return this.rightCounter.size();
    }

    public void update() {
        this.leftCounter.removeIf(MouseButton::canBeReduced);
        this.rightCounter.removeIf(MouseButton::canBeReduced);
    }

    public void update(int type) {
        switch (type) {
            case 0:
                this.leftCounter.add(new MouseButton(System.currentTimeMillis()));
                break;
            case 1:
                this.rightCounter.add(new MouseButton(System.currentTimeMillis()));
                break;
        }
    }
}
