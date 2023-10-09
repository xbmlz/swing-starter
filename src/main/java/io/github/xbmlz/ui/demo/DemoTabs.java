package io.github.xbmlz.ui.demo;

import io.github.xbmlz.ui.resources.Icons;
import io.github.xbmlz.ui.plugin.I18n;

import javax.swing.*;

public class DemoTabs extends JTabbedPane {

    public DemoTabs() {
        initComponents();
    }

    private void initComponents() {
        JPanel systemPanel = new SystemPanel();

        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        addChangeListener(e -> selectedTabChanged());
        addTab(I18n.t("demo.system.title"), Icons.SETTINGS, systemPanel);
    }

    private void selectedTabChanged() {
    }
}
