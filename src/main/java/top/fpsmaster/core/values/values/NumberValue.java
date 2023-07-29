package top.fpsmaster.core.values.values;

import top.fpsmaster.core.values.Value;

public class NumberValue<T extends Number> extends Value<T> {
    /**
     * 单次可拖动的数值
     */
    private final T inc;
    private final T min;
    private final T max;
    public boolean drag;

    public NumberValue(String name, T value, T min, T max, T inc, boolean... objects) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.setHidden(objects.length != 0 && objects[0]);
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getInc() {
        return inc;
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }
}
