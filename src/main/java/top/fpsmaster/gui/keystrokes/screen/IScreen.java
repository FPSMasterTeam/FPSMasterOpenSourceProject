package top.fpsmaster.gui.keystrokes.screen;

public interface IScreen {
    default int calculateHeight(int row) {
        return 55 + row * 23;
    }
}
