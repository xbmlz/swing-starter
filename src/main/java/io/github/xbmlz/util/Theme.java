package io.github.xbmlz.util;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.LoggingFacade;
import com.jthemedetecor.OsThemeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Theme {

    private static final Logger log = LoggerFactory.getLogger(Theme.class);

    public static final String FLAT_LIGHT = FlatLightLaf.class.getName();

    public static final String FLAT_DARK = FlatDarkLaf.class.getName();

    public static final String FLAT_MAC_LIGHT = FlatMacLightLaf.class.getName();

    public static final String FLAT_MAC_DARK = FlatMacDarkLaf.class.getName();

    public static final String LIGHT = "light";

    public static final String DARK = "dark";

    public static final String SYSTEM = "system";

    // the real colors are defined in
    // flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatLightLaf.properties and
    // flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatDarkLaf.properties
    public static final String[] ACCENT_COLOR_KEYS = {
            "Demo.accent.default", "Demo.accent.blue", "Demo.accent.purple", "Demo.accent.red",
            "Demo.accent.orange", "Demo.accent.yellow", "Demo.accent.green",
    };

    public static final String[] ACCENT_COLOR_NAMES = {
            "Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green",
    };

    public static void toggleTheme(String theme) {
        if (UIManager.getLookAndFeel().getClass().getName().equals(theme))
            return;
        String laf;
        try {
            switch (theme) {
                case DARK -> laf = FLAT_DARK;
                case SYSTEM -> {
                    detectSystemTheme();
                    return;
                }
                default -> laf = FLAT_LIGHT;
            }
            UIManager.setLookAndFeel(laf);
            FlatLaf.updateUI();
        } catch (Exception e) {
            log.warn("Failed to set theme: {}", theme, e);
        }
    }

    public static void saveTheme(String theme) {
        Prefs.put(Prefs.KEY_THEME, theme);
    }

    private static void detectSystemTheme() {
        try {
            OsThemeDetector detector = OsThemeDetector.getDetector();
            detector.registerListener(isDark -> {
                SwingUtilities.invokeLater(() -> {
                    if (isDark) {
                        toggleTheme(DARK);
                    } else {
                        toggleTheme(LIGHT);
                    }
                });
            });
            saveTheme(SYSTEM);
        } catch (Exception e) {
            log.warn("Failed to set system theme", e);
        }
    }

    public static String getTheme() {
        return Prefs.get(Prefs.KEY_THEME, LIGHT);
    }

    public static void init() {
        initTheme();
        initAccentColor();
    }

    public static void initTheme() {
        // application specific UI defaults
        FlatLaf.registerCustomDefaultsSource("themes");
        toggleTheme(getTheme());
    }

    public static void initAccentColor() {
        FlatLaf.setSystemColorGetter(name -> name.equals("accent") ? UIManager.getColor(getAccentColorKey()) : null);
        applyAccentColor(getAccentColorKey());
    }

    public static void saveAccentColor(String accentColorKey) {
        Prefs.put(Prefs.KEY_ACCENT_COLOR, accentColorKey);
    }

    public static String getAccentColorKey() {
        return Prefs.get(Prefs.KEY_ACCENT_COLOR, null);
    }

    public static void applyAccentColor(String accentColorKey) {
        FlatLaf.setSystemColorGetter(name -> name.equals("accent") ? UIManager.getColor(accentColorKey) : null);
        Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
        try {
            FlatLaf.setup(lafClass.getDeclaredConstructor().newInstance());
            FlatLaf.updateUI();
        } catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }
}
