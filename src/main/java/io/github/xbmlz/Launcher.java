package io.github.xbmlz;

import com.formdev.flatlaf.util.SystemInfo;
import io.github.xbmlz.ui.Application;
import io.github.xbmlz.util.DefaultExceptionHandler;
import io.github.xbmlz.util.LoggingOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class Launcher {

    public static void main(String[] args) {

        final Logger LOG = LogManager.getLogger("Launcher"); // init logger after log directory is set

        System.setErr(new PrintStream(new LoggingOutputStream(LogManager.getLogger("STDERR"), Level.ERROR), true));
        System.setOut(new PrintStream(new LoggingOutputStream(LogManager.getLogger("STDOUT"), Level.INFO), true));
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler());

        // print version of Java
        LOG.info("Java version: " + System.getProperty("java.version") + ", VM: " + System.getProperty("java.vm.name")
                + ", vendor: " + System.getProperty("java.vendor"));
        LOG.info("Current JAVA_HOME for running instance: " + System.getProperty("java.home"));

        if (SystemInfo.isMacOS) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "FlatLaf Theme Editor");

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (must be set on main thread and before AWT/Swing is initialized;
            //  setting it on AWT thread does not work)
            System.setProperty("apple.awt.application.appearance", "system");
        }
        Application.createAndShowGUI(args);
    }
}
