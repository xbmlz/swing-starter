package io.github.xbmlz.ui.dialog;

import io.github.xbmlz.util.I18n;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private JList<String> settingsList;

    private JPanel contentPanel;

    public SettingsDialog() {
        setSize(600, 400);
        setTitle(I18n.getString("settings.title"));
        initComponents();
    }

    private void initComponents() {
        // 右侧菜单
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement(I18n.getString("settings.general.label"));
        // 左侧菜单
        settingsList = new JList<>(listModel);
        // 右侧内容区域
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());

        // 添加菜单监听
        addSettingsListListener();
        // 添加到右侧内容区域
        contentPanel.add(new SettingsGeneralPanel(), "general");

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        splitPane.setDividerSize(5);
        splitPane.setLeftComponent(new JScrollPane(settingsList));
        splitPane.setRightComponent(contentPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.0);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane, BorderLayout.CENTER);
    }

    private void addSettingsListListener() {
        settingsList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            String selectedValue = settingsList.getSelectedValue();
            CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
            cardLayout.show(contentPanel, selectedValue);
        });
    }
}
