package com.captainbern.yggdrasil.utils;

import java.io.*;

public class IOUtils {

    public static final String DIR_SEPARATOR = File.pathSeparator;

    public static final int DEFAULT_BUFFER_SIZE = 0x1060;

    /**
     * Close methods
     */

    public static void closeQuietly(final OutputStream outputStream) {
        if(outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final InputStream inputStream) {
        if(inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Writer writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    public static void closeQuietly(final Reader reader) {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

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

    /**
     * Copy methods
     */

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long count = copyReturnLong(inputStream, outputStream);
        if(count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long count = copyReturnLong(reader, writer);
        if(count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static int copy(InputStream inputStream, Writer writer) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return copy(inputStreamReader, writer);
    }

    public static int copy(Reader reader, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        return copy(reader, writer);
    }

    public static int copy(InputStream inputStream, Writer writer, String encoding) throws IOException {
        if(encoding == null) {
            return copy(inputStream, writer);
        } else {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
            return copy(inputStreamReader, writer);
        }
    }

    public static int copy(Reader reader, OutputStream outputStream, String encoding) throws IOException {
        if(encoding == null) {
            return copy(reader, outputStream);
        } else {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, encoding);
            return copy(reader, outputStreamWriter);
        }
    }

    public static long copyReturnLong(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static long copyReturnLong(Reader reader, Writer writer) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while(-1 != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Convert utils
     */

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(reader, outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] toByteArray(Reader reader, String encoding) throws IOException {
        if(encoding == null) {
            return toByteArray(reader);
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            copy(reader, outputStream, encoding);
            return outputStream.toByteArray();
        }
    }

    public static byte[] toByteArray(final byte data) {
        return new byte[]{data};
    }

    public static byte[] toByteArray(final short data) {
        return new byte[]{
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    public static byte[] toByteArray(final char data) {
        return new byte[]{
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    public static byte[] toByteArray(final int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xFF),
                (byte) ((data >> 16) & 0xFF),
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    public static byte[] toByteArray(final long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xFF),
                (byte) ((data >> 48) & 0xFF),
                (byte) ((data >> 40) & 0xFF),
                (byte) ((data >> 32) & 0xFF),
                (byte) ((data >> 24) & 0xFF),
                (byte) ((data >> 16) & 0xFF),
                (byte) ((data >> 8) & 0xFF),
                (byte) ((data >> 0) & 0xFF)
        };
    }

    /**
     * Read methods
     */
    public static int readByte(final byte[] bytes, final int index) {
        return bytes[index] & 0xFF;
    }

    public static int readUnsignedShort(final byte[] bytes, final int index) {
        return (
                (bytes[index] & 0xFF) << 8) |
                (bytes[index + 1] & 0xFF
                );
    }

    public static short readShort(final byte[] bytes, final int index) {
        return (short) readUnsignedShort(bytes, index);
    }

    public static char readChar(final byte[] bytes, final int index) {
        return (char) ((
                (bytes[index] & 0xFF) << 8) |
                (bytes[index] & 0xFF) << 0
        );
    }

    public static int readInt(final byte[] bytes, final int index) {
        return (
                (bytes[index] & 0xFF) << 24) |
                ((bytes[index + 1] & 0xFF) << 16)|
                ((bytes[index + 2] & 0xFF) << 8) |
                (bytes[index + 3] & 0xFF
                );
    }

    public static long readLong(final byte[] bytes, final int index) {
        long l1 = readInt(bytes, index);
        long l0 = readInt(bytes, index + 4) & 0xFFFFFFFFL;
        return (l1 << 32) | l0;
    }
}
