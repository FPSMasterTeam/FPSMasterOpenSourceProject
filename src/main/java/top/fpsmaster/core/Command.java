package top.fpsmaster.core;

/**
 * @description:
 * @author: QianXia
 * @create: 2021/08/28 19:15
 **/
public abstract class Command {
    protected String name;
    protected String[] otherNames;

    public Command(String name, String... otherNames) {
        this.name = name;
        this.otherNames = otherNames;
    }

    /**
     * 命令执行
     *
     * @param args 传参不包括命令本身的名字，只有参数
     */
    public abstract void execute(String[] args);

    public final String getName() {
        return name;
    }

    public final String[] getOtherNames() {
        return otherNames;
    }
}
