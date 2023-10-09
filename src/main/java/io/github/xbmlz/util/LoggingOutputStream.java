package io.github.xbmlz.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class LoggingOutputStream extends OutputStream {

    private boolean hasBeenClosed = false;
    private byte[] buf;
    private int count;
    private int curBufLength;
    private final Logger log;
    private final Level level;

    /**
     * Creates the Logging instance to flush to the given logger.
     *
     * @param log   the Logger to write to
     * @param level the log level
     * @throws IllegalArgumentException in case if one of arguments is  null.
     */
    public LoggingOutputStream(final Logger log, final Level level) throws IllegalArgumentException {
        if (log == null || level == null) {
            throw new IllegalArgumentException("Logger or log level must be not null");
        }
        this.log = log;
        this.level = level;
        curBufLength = 2048;
        buf = new byte[curBufLength];
        count = 0;
    }

    @Override
    public void write(final int b) throws IOException {
        if (hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }
        // don't log nulls
        if (b == 0) {
            return;
        }
        // would this be writing past the buffer?
        if (count == curBufLength) {
            // grow the buffer
            final int newBufLength = curBufLength + 2048;
            final byte[] newBuf = new byte[newBufLength];
            System.arraycopy(buf, 0, newBuf, 0, curBufLength);
            buf = newBuf;
            curBufLength = newBufLength;
        }

        buf[count] = (byte) b;
        count++;
    }

    @Override
    public void flush() {
        if (count == 0) {
            return;
        }
        final byte[] bytes = new byte[count];
        System.arraycopy(buf, 0, bytes, 0, count);
        String str = new String(bytes);
        str = str.trim();
        if (!str.isEmpty())
            log.log(level, str);
        count = 0;
    }

    @Override
    public void close() {
        flush();
        hasBeenClosed = true;
    }
}