package top.fpsmaster.utils.math;

import top.fpsmaster.modules.settings.ClientSettings;

public class AnimationUtils {
    private static final float defaultSpeed = 0.125f;
    private final TimerUtil timerUtil = new TimerUtil();


    public double animate(double target, double current, double speed, boolean force) {
        return animate((float) target, (float) current, (float) speed, force);
    }

    public double animate(double target, double current, double speed) {
        return animate((float) target, (float) current, (float) speed, false);
    }

    public float animate(float target, float current, float speed) {
        return animate(target, current, speed, false);
    }

    public float animate(float target, float current, float speed, boolean force) {
        if (!ClientSettings.screenAnimation.getValue() && !force) {
            return target;
        }
        if (timerUtil.delay(16)) { // 60FPS
            boolean larger;
            boolean bl = larger = target > current;
            if (speed < 0.0f) {
                speed = 0.0f;
            } else if (speed > 1.0) {
                speed = 1.0f;
            }
            float dif = Math.max(target, current) - Math.min(target, current);
            float factor = dif * speed;
            if (factor < 0.1f) {
                factor = 0.1f;
            }
            current = larger ? (current += factor) : (current -= factor);

            timerUtil.reset();
        }
        if (Math.abs(current - target) < 0.2) {
            return target;
        } else {
            return current;
        }
    }


}
