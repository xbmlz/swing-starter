package io.github.xbmlz.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Themes {

    private static final Logger log = LoggerFactory.getLogger(Themes.class);

    public static final String FLAT_LIGHT_LAF = FlatLightLaf.class.getName();

    public static final String FLAT_DARK_LAF = FlatDarkLaf.class.getName();

    public static final String LIGHT = "light";

    public static final String DARK = "dark";

    public static final String SYSTEM = "system";

    public static void toggle(String theme) {
        if (UIManager.getLookAndFeel().getClass().getName().equals(theme))
            return;
        String laf;
        try {
            switch (theme) {
                case DARK -> laf = FLAT_DARK_LAF;
                case SYSTEM -> {
                    detectSystemTheme();
                    return;
                }
                default -> laf = FLAT_LIGHT_LAF;
            }
            UIManager.setLookAndFeel(laf);
            FlatLaf.updateUI();
        } catch (Exception e) {
            log.warn("Failed to set theme: {}", theme, e);
        }
    }

    public static void save(String theme) {
        Prefs.put(Prefs.KEY_THEME, theme);
    }

    private static void detectSystemTheme() {
        try {
            OsThemeDetector detector = OsThemeDetector.getDetector();
            detector.registerListener(isDark -> {
                SwingUtilities.invokeLater(() -> {
                    if (isDark) {
                        toggle(DARK);
                    } else {
                        toggle(LIGHT);
                    }
                });
            });
            Themes.save(SYSTEM);
        } catch (Exception e) {
            log.warn("Failed to set system theme", e);
        }
    }

    public static String get() {
        return Prefs.get(Prefs.KEY_THEME, LIGHT);
    }

    public static void init() {
        toggle(get());
    }
}
