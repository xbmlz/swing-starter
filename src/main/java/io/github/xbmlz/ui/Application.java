package io.github.xbmlz.ui;

import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import io.github.xbmlz.ui.plugin.I18n;
import io.github.xbmlz.ui.plugin.Themes;
import io.github.xbmlz.ui.plugin.Tray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Application {

    public static final String APP_NAME = "SwingStarter";

    public static final String APP_LINK = "https://github.com/xbmlz/swing-starter";

    public static MainFrame mainFrame;

    public static TrayIcon systemTray;

    public static void createAndShowGUI(String[] args) {
        I18n.setup(Locale.ENGLISH);
        SwingUtilities.invokeLater(() -> {
            Themes.setup();

            FlatInspector.install("ctrl alt shift X");
            FlatUIDefaultsInspector.install("ctrl shift alt Y");

            mainFrame = new MainFrame();
            mainFrame.setVisible(true);

            buildSystemTray();
        });
    }

    private static void buildSystemTray() {
        mainFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        List<JMenuItem> trayMenus = new ArrayList<>();
        // show/hide window
        JMenuItem showItem = new JMenuItem(I18n.t("app.show"));
        showItem.addActionListener(e -> mainFrame.setVisible(true));
        // quit
        JMenuItem quitItem = new JMenuItem(I18n.t("app.quit"));
        quitItem.addActionListener(e -> mainFrame.quit());
        trayMenus.add(showItem);
        trayMenus.add(quitItem);

        systemTray = Tray.build(mainFrame.getIconImage(), Application.APP_NAME, trayMenus);
        systemTray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mainFrame.setVisible(true);
                }
            }
        });
    }


}
