package io.github.xbmlz.ui.plugin;

import com.formdev.flatlaf.util.LoggingFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Tray {

    public static TrayIcon build(Image image, String tooltip, List<JMenuItem> menuItems) {
        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        JPopupMenu trayMenu = new JPopupMenu() {
            @Override
            protected void firePopupMenuWillBecomeInvisible() {
                dialog.setVisible(false);
                super.firePopupMenuWillBecomeInvisible();
            }
        };
        for (JMenuItem menuItem : menuItems) {
            trayMenu.add(menuItem);
        }
        TrayIcon tray = new TrayIcon(image, tooltip);
        // create system tray icon.
        tray.setImageAutoSize(true);
        tray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.isPopupTrigger()) {
                    dialog.setSize(trayMenu.getPreferredSize());
                    dialog.setLocation(e.getX(), e.getY() - trayMenu.getHeight());
                    dialog.setVisible(true);
                    trayMenu.show(dialog, 0, 0);
                }
            }
        });
        // add the tray icon to the system tray.
        try {
            java.awt.SystemTray.getSystemTray().add(tray);
        } catch (AWTException ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
        return tray;
    }
}
