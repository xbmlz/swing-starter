package io.github.xbmlz;

import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.util.SystemInfo;
import io.github.xbmlz.ui.MainFrame;
import io.github.xbmlz.ui.plugin.I18n;
import io.github.xbmlz.ui.plugin.Themes;
import io.github.xbmlz.ui.plugin.Tray;
import io.github.xbmlz.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static MainFrame mainFrame;

    public static TrayIcon systemTray;

    public static void main(String[] args) {
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
        I18n.setup();
        SwingUtilities.invokeLater(() -> {
            Themes.setup();

            FlatInspector.install("ctrl alt shift X");
            FlatUIDefaultsInspector.install("ctrl shift alt Y");

            mainFrame = new MainFrame();
            mainFrame.setVisible(true);

            buildSystemTray();
        });
    }

    public static void buildSystemTray() {
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        List<JMenuItem> trayMenus = new ArrayList<>();
        // show/hide window
        JMenuItem showItem = new JMenuItem(I18n.get("app.show"));
        showItem.addActionListener(e -> mainFrame.setVisible(true));
        // quit
        JMenuItem quitItem = new JMenuItem(I18n.get("app.quit"));
        quitItem.addActionListener(e -> quit());
        trayMenus.add(showItem);
        trayMenus.add(quitItem);

        systemTray = Tray.build(mainFrame.getIconImage(), Constants.APP_NAME, trayMenus);
        systemTray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mainFrame.setVisible(true);
                }
            }
        });
    }

    public static void quit() {
        mainFrame.dispose();
        System.exit(0);
    }
}
