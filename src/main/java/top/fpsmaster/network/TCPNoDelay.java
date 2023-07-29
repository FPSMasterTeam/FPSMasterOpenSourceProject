
package top.fpsmaster.network;

public class TCPNoDelay {
    private static boolean active = true;

    public static String isActive() {
        return active ? "True" : "False";
    }

    public static void toggle() {
        active = !active;
    }
}
