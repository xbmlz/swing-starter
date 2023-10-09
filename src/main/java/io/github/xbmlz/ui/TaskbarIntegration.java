package io.github.xbmlz.ui;

import java.awt.*;

public class TaskbarIntegration {

    private Taskbar taskbar = null;

    public TaskbarIntegration() {
        if (Taskbar.isTaskbarSupported()) {
            taskbar = Taskbar.getTaskbar();
        }
    }

    public void setProgressState(Window w, int progress) {
        if (taskbar != null && taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE_WINDOW))
            taskbar.setWindowProgressValue(w, progress);
    }

    public void setIntermediateProgress(Window w) {
        if (taskbar != null && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
            taskbar.setWindowProgressState(w, Taskbar.State.INDETERMINATE);
    }

    public void clearState(Window w) {
        if (taskbar != null && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW)) {
            taskbar.setWindowProgressState(w, Taskbar.State.NORMAL);
            taskbar.setWindowProgressState(w, Taskbar.State.OFF);
            setProgressState(w, -1);
        }
    }

    public void setWarningIndicator(Window w) {
        if (taskbar != null && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
            taskbar.setWindowProgressState(w, Taskbar.State.PAUSED);
    }

    public void setErrorIndicator(Window w) {
        if (taskbar != null && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW))
            taskbar.setWindowProgressState(w, Taskbar.State.ERROR);
    }
}
