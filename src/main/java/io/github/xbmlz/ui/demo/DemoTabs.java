package io.github.xbmlz.ui.demo;

import io.github.xbmlz.ui.assets.Icons;
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
        addTab(I18n.get("demo.tabs.system.title"), Icons.SETTINGS, systemPanel);
    }

    private void selectedTabChanged() {
    }
}
