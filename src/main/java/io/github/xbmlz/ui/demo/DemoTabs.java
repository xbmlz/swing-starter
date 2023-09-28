package io.github.xbmlz.ui.demo;

import io.github.xbmlz.util.Icons;
import io.github.xbmlz.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class DemoTabs extends JTabbedPane {

    private static final Logger log = LoggerFactory.getLogger(DemoTabs.class);

    public DemoTabs() {
        addChangeListener(e -> selectedTabChanged());
        initComponents();
    }

    private void initComponents() {
        addTab(Messages.getString("settings"), Icons.SETTINGS, new SettingsPanel());
    }

    private void selectedTabChanged() {
        log.debug("selectedTabChanged");
    }
}
