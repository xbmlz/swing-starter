package io.github.xbmlz.ui.component;

import io.github.xbmlz.App;
import io.github.xbmlz.util.Constants;
import io.github.xbmlz.util.I18n;
import io.github.xbmlz.util.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tray {

    private static final Logger log = LoggerFactory.getLogger(Tray.class);

    public static void init() {
        if (!java.awt.SystemTray.isSupported()) {
            log.warn("System tray is not supported");
        }
        // Popup menu not support Chinese, customize it. Java awt popup menu 不支持中文，需要自定义
        JDialog trayDialog = new JDialog();
        trayDialog.setUndecorated(true);
        JPopupMenu trayMenu = new JPopupMenu() {
            @Override
            protected void firePopupMenuWillBecomeInvisible() {
                trayDialog.setVisible(false);
                super.firePopupMenuWillBecomeInvisible();
            }
        };

        JMenuItem showItem = new JMenuItem(I18n.getString("app.show"));
        showItem.addActionListener(e -> App.mainFrame.setVisible(true));
        trayMenu.add(showItem);

        JMenuItem exitItem = new JMenuItem(I18n.getString("app.exit"));
        exitItem.addActionListener(e -> App.mainFrame.quit());
        trayMenu.add(exitItem);

        // create system tray icon.
        TrayIcon tray = buildTrayIcon(Images.APP_ICON_PNG_32, trayDialog, trayMenu);
        // add the tray icon to the system tray.
        try {
            java.awt.SystemTray.getSystemTray().add(tray);
        } catch (AWTException e) {
            log.error("Can't add tray icon", e);
        }
    }

    private static TrayIcon buildTrayIcon(ImageIcon trayIcon, JDialog trayDialog, JPopupMenu trayMenu) {
        TrayIcon tray = new TrayIcon(trayIcon.getImage(), Constants.APP_NAME);
        tray.setImageAutoSize(true);
        tray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    App.mainFrame.setVisible(true);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && e.isPopupTrigger()) {
                    trayDialog.setSize(trayMenu.getPreferredSize());
                    trayDialog.setLocation(e.getX(), e.getY() - trayMenu.getHeight());
                    trayDialog.setVisible(true);
                    trayMenu.show(trayDialog, 0, 0);
                }
            }
        });
        return tray;
    }
}
