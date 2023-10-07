package io.github.xbmlz.util;

import java.util.prefs.Preferences;

public class Prefs {

    public static final String KEY_PREFS = "swing-starter";

    public static final String KEY_WINDOW_BOUNDS = "window.bounds";

    public static final String KEY_THEME = "theme";

    public static final String KEY_LANGUAGE = "language";

    public static final Preferences PREFS = Preferences.userRoot().node(KEY_PREFS);
    public static final String KEY_ACCENT_COLOR = "accent.color";

    public static void put(String key, String value) {
        PREFS.put(key, value);
    }

    public static String get(String key, String defaultValue) {
        return PREFS.get(key, defaultValue);
    }
}
