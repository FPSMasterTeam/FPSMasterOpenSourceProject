package top.fpsmaster.core.vac.check.impl.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

public class MiscCheck {
    public static final int BUFFER_SIZE = 1 << 12;
    private static final HashMap<String, byte[]> bytes = new HashMap<>();
    private static final ThreadLocal<byte[]> loadBuffer = new ThreadLocal<>();

    private static final String[] RESERVED_NAMES = {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

    public MiscCheck() {
        bytes.put("EntityPlayerSP", getClassBytes(EntityPlayerSP.class.getName()));
        bytes.put("Minecraft", getClassBytes(Minecraft.class.getName()));
    }

    public void addValue(String name, byte[] bytes) {
        MiscCheck.bytes.remove(name);
        MiscCheck.bytes.put(name, bytes);
    }

    public static HashMap<String, byte[]> getValue() {
        return bytes;
    }

    public static byte[] getClassBytes(String name) {
        if (name.indexOf('.') == -1) {
            for (final String reservedName : RESERVED_NAMES) {
                if (name.toUpperCase(Locale.ENGLISH).startsWith(reservedName)) {
                    final byte[] data = getClassBytes("_" + name);
                    if (data != null) {
                        return data;
                    }
                }
            }
        }
        InputStream classStream = null;
        try {
            final String resourcePath = name.replace('.', '/').concat(".class");
            final URL classResource = MiscCheck.class.getClassLoader().getResource(resourcePath);
            if (classResource == null) return null;
            classStream = classResource.openStream();
            return readFully(classStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilently(classStream);
        }
        return null;
    }

    private static byte[] readFully(InputStream stream) {
        try {
            byte[] buffer = getOrCreateBuffer();

            int read;
            int totalLength = 0;
            while ((read = stream.read(buffer, totalLength, buffer.length - totalLength)) != -1) {
                totalLength += read;

                // Extend our buffer
                if (totalLength >= buffer.length - 1) {
                    byte[] newBuffer = new byte[buffer.length + BUFFER_SIZE];
                    System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
                    buffer = newBuffer;
                }
            }

            final byte[] result = new byte[totalLength];
            System.arraycopy(buffer, 0, result, 0, totalLength);
            return result;
        } catch (Throwable t) {
            return new byte[0];
        }
    }

    private static byte[] getOrCreateBuffer() {
        byte[] buffer = loadBuffer.get();
        if (buffer == null) {
            loadBuffer.set(new byte[BUFFER_SIZE]);
            buffer = loadBuffer.get();
        }
        return buffer;
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
