package io.github.xbmlz.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOG = LogManager.getLogger("Exception");

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        LOG.error("Uncaught exception in " + thread.getName(), throwable);
    }
}