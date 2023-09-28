package io.github.xbmlz.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.jthemedetecor.OsThemeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Themes {

    private static final Logger log = LoggerFactory.getLogger(Themes.class);

    public static final String FLAT_LIGHT = FlatLightLaf.class.getName();

    public static final String FLAT_DARK = FlatDarkLaf.class.getName();

    public static final String FLAT_MAC_LIGHT = FlatMacLightLaf.class.getName();

    public static final String FLAT_MAC_DARK = FlatMacDarkLaf.class.getName();

    public static final String LIGHT = "light";

    public static final String DARK = "dark";

    public static final String SYSTEM = "system";

    public static void toggle(String theme) {
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

//    public static void setAccentColor(String accentColorKey) {
//        for( int i = 0; i < accentColorButtons.length; i++ ) {
//            if( accentColorButtons[i].isSelected() ) {
//                accentColorKey = accentColorKeys[i];
//                break;
//            }
//        }
//
//        accentColor = (accentColorKey != null && accentColorKey != accentColorKeys[0])
//                ? UIManager.getColor( accentColorKey )
//                : null;
//
//        Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
//        try {
//            FlatLaf.setup( lafClass.getDeclaredConstructor().newInstance() );
//            FlatLaf.updateUI();
//        } catch( Exception ex ) {
//            LoggingFacade.INSTANCE.logSevere( null, ex );
//        }
//    }

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
        // application specific UI defaults
        FlatLaf.registerCustomDefaultsSource( "themes" );
        toggle(get());
    }
}
