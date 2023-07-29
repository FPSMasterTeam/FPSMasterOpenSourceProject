package top.fpsmaster.utils.math;

public class MouseButton {
    private final long lastMs;

    public MouseButton(long lastMs) {
        this.lastMs = lastMs;
    }

    public boolean canBeReduced() {
        return System.currentTimeMillis() - lastMs >= 1000L;
    }

    public long getLastMs() {
        return lastMs;
    }
}