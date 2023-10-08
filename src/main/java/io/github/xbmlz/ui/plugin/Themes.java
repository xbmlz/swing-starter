package io.github.xbmlz.ui.plugin;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.LoggingFacade;
import com.jthemedetecor.OsThemeDetector;

import javax.swing.*;

public class Themes {

    public final static String DEFAULT_THEME = FlatLightLaf.class.getName();

    public final static ThemeInfo[] BUNDLED_THEMES = {
            new ThemeInfo("FlatLaf Light", FlatLightLaf.class.getName(), false),
            new ThemeInfo("FlatLaf Dark", FlatDarkLaf.class.getName(), true),
            new ThemeInfo("FlatLaf IntelliJ", FlatIntelliJLaf.class.getName(), false),
            new ThemeInfo("FlatLaf Darcula", FlatDarculaLaf.class.getName(), true),
            new ThemeInfo("FlatLaf macOS Light", FlatMacLightLaf.class.getName(), false),
            new ThemeInfo("FlatLaf macOS Dark", FlatMacDarkLaf.class.getName(), true),
            // IntelliJ themes
            new ThemeInfo("Arc", FlatArcIJTheme.class.getName(), false),
            new ThemeInfo("Arc - Orange", FlatArcOrangeIJTheme.class.getName(), false),
            new ThemeInfo("Arc Dark", FlatArcDarkIJTheme.class.getName(), true),
            new ThemeInfo("Arc Dark - Orange", FlatArcDarkOrangeIJTheme.class.getName(), true),
            new ThemeInfo("Carbon", FlatCarbonIJTheme.class.getName(), false),
            new ThemeInfo("Cobalt 2", FlatCobalt2IJTheme.class.getName(), false),
            new ThemeInfo("Cyan light", FlatCyanLightIJTheme.class.getName(), false),
            new ThemeInfo("Dark Flat", FlatDarkFlatIJTheme.class.getName(), true),
            new ThemeInfo("Dark purple", FlatDarkPurpleIJTheme.class.getName(), true),
            new ThemeInfo("Dracula", FlatDraculaIJTheme.class.getName(), true),
            new ThemeInfo("Gradianto Dark Fuchsia", FlatGradiantoDarkFuchsiaIJTheme.class.getName(), true),
            new ThemeInfo("Gradianto Deep Ocean", FlatGradiantoDeepOceanIJTheme.class.getName(), true),
            new ThemeInfo("Gradianto Midnight Blue", FlatGradiantoMidnightBlueIJTheme.class.getName(), true),
            new ThemeInfo("Gradianto Nature Green", FlatGradiantoNatureGreenIJTheme.class.getName(), true),
            new ThemeInfo("Gray", FlatGrayIJTheme.class.getName(), false),
            new ThemeInfo("Gruvbox Dark Hard", FlatGruvboxDarkHardIJTheme.class.getName(), true),
            new ThemeInfo("Gruvbox Dark Medium", FlatGruvboxDarkMediumIJTheme.class.getName(), true),
            new ThemeInfo("Gruvbox Dark Soft", FlatGruvboxDarkSoftIJTheme.class.getName(), true),
            new ThemeInfo("Hiberbee Dark", FlatHiberbeeDarkIJTheme.class.getName(), true),
            new ThemeInfo("High contrast", FlatHighContrastIJTheme.class.getName(), true),
            new ThemeInfo("Light Flat", FlatLightFlatIJTheme.class.getName(), false),
            new ThemeInfo("Material Design Dark", FlatMaterialDesignDarkIJTheme.class.getName(), true),
            new ThemeInfo("Monocai", FlatMonocaiIJTheme.class.getName(), true),
            new ThemeInfo("Monokai Pro", FlatMonokaiProIJTheme.class.getName(), true),
            new ThemeInfo("Nord", FlatNordIJTheme.class.getName(), true),
            new ThemeInfo("One Dark", FlatOneDarkIJTheme.class.getName(), true),
            new ThemeInfo("Solarized Dark", FlatSolarizedDarkIJTheme.class.getName(), true),
            new ThemeInfo("Solarized Light", FlatSolarizedLightIJTheme.class.getName(), false),
            new ThemeInfo("Spacegray", FlatSpacegrayIJTheme.class.getName(), true),
            new ThemeInfo("Vuesion", FlatVuesionIJTheme.class.getName(), true),
            new ThemeInfo("Xcode-Dark", FlatXcodeDarkIJTheme.class.getName(), true),
            // Material themes
            new ThemeInfo("Arc Dark (Material)", FlatArcDarkIJTheme.class.getName(), true),
            new ThemeInfo("Atom One Dark (Material)", FlatAtomOneDarkIJTheme.class.getName(), true),
            new ThemeInfo("Atom One Light (Material)", FlatAtomOneLightIJTheme.class.getName(), false),
            new ThemeInfo("Dracula (Material)", FlatDraculaIJTheme.class.getName(), true),
            new ThemeInfo("GitHub (Material)", FlatGitHubIJTheme.class.getName(), false),
            new ThemeInfo("GitHub Dark (Material)", FlatGitHubDarkIJTheme.class.getName(), true),
            new ThemeInfo("Light Owl (Material)", FlatLightOwlIJTheme.class.getName(), true),
            new ThemeInfo("Material Darker (Material)", FlatMaterialDarkerIJTheme.class.getName(), true),
            new ThemeInfo("Material Deep Ocean (Material)", FlatMaterialDeepOceanIJTheme.class.getName(), true),
            new ThemeInfo("Material Lighter (Material)", FlatMaterialLighterIJTheme.class.getName(), false),
            new ThemeInfo("Material Oceanic (Material)", FlatMaterialOceanicIJTheme.class.getName(), true),
            new ThemeInfo("Material Palenight (Material)", FlatMaterialPalenightIJTheme.class.getName(), true),
            new ThemeInfo("Monokai Pro (Material)", FlatMonokaiProIJTheme.class.getName(), true),
            new ThemeInfo("Moonlight (Material)", FlatMoonlightIJTheme.class.getName(), true),
            new ThemeInfo("Night Owl (Material)", FlatNightOwlIJTheme.class.getName(), true),
            new ThemeInfo("Solarized Dark (Material)", FlatSolarizedDarkIJTheme.class.getName(), true),
            new ThemeInfo("Solarized Light (Material)", FlatSolarizedLightIJTheme.class.getName(), false),
    };

    public record ThemeInfo(String name, String lafClassName, Boolean isDark) {
        @Override
        public String toString() {
            return name;
        }
    }

    public static void setup() {
        applyTheme(DEFAULT_THEME);
    }

    public static void applyTheme(String lafClassName) {
        if (lafClassName == null) {
            detectSystemTheme();
        }
        try {
            UIManager.setLookAndFeel(lafClassName);
            FlatLaf.updateUI();
        } catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }

    private static void detectSystemTheme() {
        try {
            OsThemeDetector detector = OsThemeDetector.getDetector();
            detector.registerListener(isDark -> {
                if (isDark) {
                    applyTheme(FlatDarkLaf.class.getName());
                } else {
                    applyTheme(FlatLightLaf.class.getName());
                }
            });
        } catch (Exception ex) {
            LoggingFacade.INSTANCE.logSevere("Failed to set system theme", ex);
        }
    }

    public static ThemeInfo getCurrentThemeInfo() {
        return getThemeInfo(UIManager.getLookAndFeel().getClass().getName());
    }

    public static ThemeInfo getThemeInfo(String lafClassName) {
        for (ThemeInfo themeInfo : BUNDLED_THEMES) {
            if (themeInfo.lafClassName().equals(lafClassName)) {
                return themeInfo;
            }
        }
        return null;
    }
}
