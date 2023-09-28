package io.github.xbmlz.ui;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.StringUtils;
import com.formdev.flatlaf.util.UIScale;
import io.github.xbmlz.util.Constants;
import io.github.xbmlz.util.Messages;
import io.github.xbmlz.util.Prefs;
import io.github.xbmlz.ui.component.TopMenubar;
import io.github.xbmlz.ui.demo.DemoTabs;
import io.github.xbmlz.util.Themes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    public MainFrame() {
        setTitle(Constants.APP_NAME);
        setIconImages(FlatSVGUtils.createWindowIconImages(Constants.APP_ICON));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        restoreWindowBounds();
        Themes.init();
        Messages.init();
        initSystemTray();
        initComponents();
    }

    private void initComponents() {
        setJMenuBar(new TopMenubar());
        add(new DemoTabs());
    }

    private void initSystemTray() {
        if (!SystemTray.isSupported()) {
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

        JMenuItem showItem = new JMenuItem(Messages.getString("app.show"));
        showItem.addActionListener(e -> setVisible(true));
        trayMenu.add(showItem);

        JMenuItem exitItem = new JMenuItem(Messages.getString("app.exit"));
        exitItem.addActionListener(e -> quit());
        trayMenu.add(exitItem);

        // create system tray icon.
        ImageIcon trayIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(Constants.APP_ICON_PNG)));
        TrayIcon tray = new TrayIcon(trayIcon.getImage(), Constants.APP_NAME);
        tray.setImageAutoSize(true);
        tray.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    setVisible(true);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && e.isPopupTrigger()) {
                    trayDialog.setSize(trayMenu.getPreferredSize());
                    trayDialog.setLocation(e.getX(), e.getY() - trayMenu.getHeight());
                    trayDialog.setVisible(true);
                    trayMenu.show(trayDialog, 0, 0);
                }
            }
        });
        // add the tray icon to the system tray.
        try {
            SystemTray.getSystemTray().add(tray);
        } catch (AWTException e) {
            log.error("Can't add tray icon", e);
        }
    }

    private void quit() {
        storeWindowBounds();
        System.exit(0);
    }

    private void restoreWindowBounds() {
        String windowBoundsStr = Prefs.get(Prefs.KEY_WINDOW_BOUNDS, null);
        if (windowBoundsStr != null) {
            java.util.List<String> list = StringUtils.split(windowBoundsStr, ',');
            if (list.size() >= 4) {
                try {
                    int x = UIScale.scale(Integer.parseInt(list.get(0)));
                    int y = UIScale.scale(Integer.parseInt(list.get(1)));
                    int w = UIScale.scale(Integer.parseInt(list.get(2)));
                    int h = UIScale.scale(Integer.parseInt(list.get(3)));

                    // limit to screen size
                    GraphicsConfiguration gc = getGraphicsConfiguration();
                    if (gc != null) {
                        Rectangle screenBounds = gc.getBounds();
                        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
                        Rectangle r = FlatUIUtils.subtractInsets(screenBounds, screenInsets);

                        w = Math.min(w, r.width);
                        h = Math.min(h, r.height);
                        x = Math.max(Math.min(x, r.width - w), r.x);
                        y = Math.max(Math.min(y, r.height - h), r.y);
                    }

                    setBounds(x, y, w, h);
                    return;
                } catch (NumberFormatException ex) {
                    // ignore
                }
            }
        }

        // default window size
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    private void storeWindowBounds() {
        Rectangle r = getBounds();
        int x = UIScale.unscale(r.x);
        int y = UIScale.unscale(r.y);
        int width = UIScale.unscale(r.width);
        int height = UIScale.unscale(r.height);
        Prefs.put(Prefs.KEY_WINDOW_BOUNDS, x + "," + y + ',' + width + ',' + height);
    }
}
