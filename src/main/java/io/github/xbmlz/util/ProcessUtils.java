package io.github.xbmlz.util;

public class ProcessUtils {

    public static void relaunch(String className) {
        try {
            String javaCommand = System.getProperty("java.home") + "/bin/java";
            String[] command = {javaCommand, "-cp", System.getProperty("java.class.path"), className};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
