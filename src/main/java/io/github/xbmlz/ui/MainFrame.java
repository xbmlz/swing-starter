package io.github.xbmlz.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.StringUtils;
import com.formdev.flatlaf.util.SystemInfo;
import com.formdev.flatlaf.util.UIScale;
import io.github.xbmlz.ui.demo.DemoTabs;
import io.github.xbmlz.ui.plugin.Prefs;
import io.github.xbmlz.util.DesktopUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;

public class MainFrame extends JFrame {

    public static MainMenuBar mainMenuBar;

    public static MainToolBar mainToolBar;

    public static DemoTabs demoTabs;

    public static StatusBar statusBar;


    public MainFrame() {
        setTitle(Application.APP_NAME);
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/logo.svg"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();

        // restore window size and location from preferences
        restoreWindowBounds();

        // macOS  (see https://www.formdev.com/flatlaf/macos/)
        if (SystemInfo.isMacOS) {
            if (SystemInfo.isMacFullWindowContentSupported) {
                // expand window content into window title bar and make title bar transparent
                getRootPane().putClientProperty("apple.awt.fullWindowContent", true);
                getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
            }

            // hide window title
            if (SystemInfo.isJava_17_orLater)
                getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
            else
                setTitle(null);

            // add gap to left side of toolbar
            // controlPanel.add(Box.createHorizontalStrut(70), 0);

            // enable full screen mode for this window (for Java 8 - 10; not necessary for Java 11+)
            if (!SystemInfo.isJava_11_orLater)
                getRootPane().putClientProperty("apple.awt.fullscreenable", true);
        }

        // integrate into macOS screen menu
        FlatDesktop.setAboutHandler(this::showAboutDialog);
        FlatDesktop.setQuitHandler(response -> {
            saveWindowBounds();
            response.performQuit();
        });
    }

    private void initComponents() {
        mainMenuBar = new MainMenuBar();
        setJMenuBar(mainMenuBar);

        mainToolBar = new MainToolBar();
        add(mainToolBar, BorderLayout.NORTH);

        demoTabs = new DemoTabs();
        add(demoTabs);

        statusBar = new StatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    public void quit() {
        dispose();
        System.exit(0);
    }

    public void showAboutDialog() {
        JLabel titleLabel = new JLabel(Application.APP_NAME);
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

        String link = Application.APP_LINK;
        JLabel linkLabel = new JLabel("<html><a href=\"#\">" + link + "</a></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DesktopUtils.browseSafe(link);
            }
        });

        JOptionPane.showMessageDialog(this,
                new Object[]{
                        titleLabel,
                        "Swing Application Fast Development Boilerplate",
                        " ",
                        "Copyright 2019-" + Year.now() + " Xbmlz",
                        linkLabel,
                },
                "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveWindowBounds() {
        Rectangle r = getBounds();
        int x = UIScale.unscale(r.x);
        int y = UIScale.unscale(r.y);
        int width = UIScale.unscale(r.width);
        int height = UIScale.unscale(r.height);
        Prefs.put(Prefs.KEY_WINDOW_BOUNDS, x + "," + y + ',' + width + ',' + height);
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
}
