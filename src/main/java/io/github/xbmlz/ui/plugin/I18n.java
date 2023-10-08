package io.github.xbmlz.ui.plugin;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * I18n plugin - Internationalization
 *
 * @author chenxc
 * @version 1.0.0
 */
public class I18n {

    private static final String BUNDLE_NAME = "messages/messages";

    public static final String[] BUNDLED_LOCALES = {
            Locale.ENGLISH.getLanguage(),
            Locale.CHINESE.getLanguage()
    };

    public static void setup(Locale locale) {
        Locale.setDefault(locale);
    }

    public static void setup() {
        Locale.setDefault(getLocale());
    }

    public static Locale[] getAvailableLocales() {
        return Locale.getAvailableLocales();
    }

    public static void setLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static String getLocaleLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }

    /**
     * Get string from messages.properties
     *
     * <pre>
     *     // key=value
     *     String value = I18n.t("key")
     * </pre>
     *
     * @param key String key
     * @return String value
     */
    public static String get(String key) {
        return getBundle().getString(key);
    }

    /**
     * Get string from messages.properties
     *
     * <pre>
     *     // app.name=Hello {0}
     *     String appName = I18n.t("app.name", "World")
     * </pre>
     *
     * @param key    String key
     * @param format Object format
     * @return String value
     */
    public static String get(String key, Object... format) {
        return String.format(getBundle().getString(key), format);
    }

}
