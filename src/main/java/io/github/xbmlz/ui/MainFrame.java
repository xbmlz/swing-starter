package io.github.xbmlz.ui;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.StringUtils;
import com.formdev.flatlaf.util.UIScale;
import io.github.xbmlz.ui.component.Tray;
import io.github.xbmlz.ui.component.TopMenubar;
import io.github.xbmlz.util.Constants;
import io.github.xbmlz.util.I18n;
import io.github.xbmlz.util.Prefs;
import io.github.xbmlz.util.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    public MainFrame() {
        setTitle(Constants.APP_NAME);
        setIconImages(FlatSVGUtils.createWindowIconImages(Constants.APP_ICON));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        restoreWindowBounds();
        initComponents();
        Tray.init();
        Theme.init();
        I18n.init();
    }

    private void initComponents() {
        setJMenuBar(new TopMenubar());
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

    public void quit() {
        storeWindowBounds();
        dispose();
        System.exit(0);
    }
}
