package top.fpsmaster.utils.os;

import java.io.*;

public class FileUtils {
    public static String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String readFile(String fileName) {
        StringBuilder result = new StringBuilder();

        try {
            File file = new File(fileName).getAbsoluteFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void saveFile(String fileName, String context) {
        File file = new File(fileName).getAbsoluteFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(context);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
