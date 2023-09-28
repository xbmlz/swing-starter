package io.github.xbmlz.util;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.util.StringUtils;
import io.github.xbmlz.App;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static final String BUNDLE_NAME = "messages/messages";

    public static final String LOCAL_ZH = "zh_CN";

    public static final String LOCAL_EN = "en_US";

    public Messages() {
        // do nothing
        System.out.println("Messages");
    }

    public static String getString(String key) {
        return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(getLocale())).getString(key);
    }

    public static String getLocale() {
        return Prefs.get(Prefs.KEY_LANGUAGE, Locale.getDefault().getLanguage());
    }

    public static void setLocal(String locale) {
        if (StringUtils.isEmpty(locale))
            return;
        if (locale.equals(LOCAL_EN)) {
            Locale.setDefault(Locale.US);
        } else if (locale.equals(LOCAL_ZH)) {
            Locale.setDefault(Locale.CHINA);
        }
        Prefs.put(Prefs.KEY_LANGUAGE, Locale.getDefault().getLanguage());
        // 重新绘制
        App.mainFrame.revalidate();
        App.mainFrame.repaint();
    }
}