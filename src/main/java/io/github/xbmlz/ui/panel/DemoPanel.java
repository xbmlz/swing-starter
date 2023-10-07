package io.github.xbmlz.ui.panel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DemoPanel extends JPanel {

    private JTabbedPane demoTabbedPane;

    public DemoPanel() {

        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout(
                "insets 0,hidemode 3",
                // columns
                "[grow,fill]",
                // rows
                "[]"
       ));
        demoTabbedPane = new JTabbedPane();
        demoTabbedPane.setTabPlacement(JTabbedPane.LEFT);
        demoTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        demoTabbedPane.addTab("Tab 1", new JLabel("Tab 1"));
        demoTabbedPane.addTab("Tab 2", new JLabel("Tab 2"));
        demoTabbedPane.addTab("Tab 3", new JLabel("Tab 3"));
        demoTabbedPane.addTab("Tab 4", new JLabel("Tab 4"));
        add(demoTabbedPane, "height 100%");
    }
}
