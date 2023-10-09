package io.github.xbmlz.ui;

import io.github.xbmlz.ui.component.JEmptyBox;
import io.github.xbmlz.ui.component.util.ThreadUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StatusBar extends JPanel {

    private JLabel memoryUsageLabel;

    public StatusBar() {
        initComponents();
        updateMemoryUsage();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 5));
        // left
        {
            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
            left.setOpaque(false);
            left.add(new JEmptyBox(10, 5));

            JLabel info = new JLabel(getInfo());
            info.setCursor(new Cursor(Cursor.HAND_CURSOR));
            left.add(info);

            left.add(new JEmptyBox(3, 3));
            add("West", left);
        }
        // right
        {
            JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
            right.setOpaque(false);
            right.add(new JEmptyBox(3, 3));

            memoryUsageLabel = new JLabel("Memory Usage: 0 of 0M");

            right.add(memoryUsageLabel);

            right.add(new JEmptyBox(10, 5));
            add("East", right);
        }

        setPreferredSize(new Dimension(24, 24));
    }

    private String getInfo() {
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        return String.format("Java: %s, OS: %s %s (%s)", javaVersion, osName, osVersion, osArch);
    }

    private void updateMemoryUsage() {
        Timer timer = new Timer(1000, e -> {
            ThreadUtils.runOnSwingThread(() -> {
                Runtime rt = Runtime.getRuntime();
                long maxMem = rt.maxMemory() >> 20;
                long allocatedMem = rt.totalMemory() >> 20;
                long usedMem = allocatedMem - (rt.freeMemory() >> 20);
                String text = String.format("Memory Usage: %d of %dM", usedMem, maxMem);
                memoryUsageLabel.setText(text);
            });
        });
        timer.setRepeats(true);
        timer.start();
    }

    @Override
    public Border getBorder() {
        return BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground"));
    }
}
