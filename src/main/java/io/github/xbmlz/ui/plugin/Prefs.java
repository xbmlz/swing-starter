package io.github.xbmlz.ui.plugin;

import java.util.prefs.Preferences;

public class Prefs {

    private static final String KEY_ROOT = "swing-starter";
    public static final String KEY_WINDOW_BOUNDS = "window.bounds";
    public static final Preferences PREFS = Preferences.userRoot().node(KEY_ROOT);

    public static void put(String key, String value) {
        PREFS.put(key, value);
    }

    public static String get(String key, String defaultValue) {
        return PREFS.get(key, defaultValue);
    }

}
