package com.captainbern.yggdrasil.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    public static void closeQuietly(final Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if(closeables == null)
            return;
        for(Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return toByteArray(inputStream, true);
    }

    public static byte[] toByteArray(InputStream inputStream, boolean close) throws IOException {
        if(inputStream == null) {
            throw new IllegalArgumentException("InputStream can't be NULL!");
        }

        try {

            byte[] bytes = new byte[inputStream.available()];
            int length = 0;

            while(true) {
                int data = inputStream.read(bytes, length, bytes.length - length);

                if (data == -1) {
                    if (length < bytes.length) {
                        byte[] c = new byte[length];
                        System.arraycopy(bytes, 0, c, 0, length);
                        bytes = c;
                    }
                    return bytes;
                }

                length += data;
                if (length == bytes.length) {
                    int last = inputStream.read();
                    if (last < 0) {
                        return bytes;
                    }
                    byte[] c = new byte[bytes.length + 1000];
                    System.arraycopy(bytes, 0, c, 0, length);
                    c[length++] = (byte) last;
                    bytes = c;
                }
            }

        } finally {
            if(close) {
                closeQuietly(inputStream);
            }
        }
    }
}
