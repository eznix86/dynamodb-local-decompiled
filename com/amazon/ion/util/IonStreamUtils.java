/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazon.ion.util;

import com.amazon.ion.IonException;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.impl._Private_IonConstants;
import com.amazon.ion.impl._Private_ListWriter;
import com.amazon.ion.util.GzipOrRawInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IonStreamUtils {
    public static boolean isIonBinary(byte[] buffer) {
        return buffer != null && IonStreamUtils.isIonBinary(buffer, 0, buffer.length);
    }

    public static boolean isIonBinary(byte[] buffer, int offset, int length) {
        return IonStreamUtils.cookieMatches(_Private_IonConstants.BINARY_VERSION_MARKER_1_0, buffer, offset, length);
    }

    public static boolean isGzip(byte[] buffer, int offset, int length) {
        return IonStreamUtils.cookieMatches(GzipOrRawInputStream.GZIP_HEADER, buffer, offset, length);
    }

    private static boolean cookieMatches(byte[] cookie, byte[] buffer, int offset, int length) {
        if (buffer == null || length < cookie.length) {
            return false;
        }
        for (int i = 0; i < cookie.length; ++i) {
            if (cookie[i] == buffer[offset + i]) continue;
            return false;
        }
        return true;
    }

    public static InputStream unGzip(InputStream in) throws IOException {
        return new GzipOrRawInputStream(in);
    }

    public static void throwAsIonException(Exception e) {
        throw new IonException(e);
    }

    public static void writeBoolList(IonWriter writer, boolean[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeBoolList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeBool(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeFloatList(IonWriter writer, float[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeFloatList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeFloat(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeFloatList(IonWriter writer, double[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeFloatList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeFloat(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeIntList(IonWriter writer, byte[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeIntList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeInt(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeIntList(IonWriter writer, short[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeIntList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeInt(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeIntList(IonWriter writer, int[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeIntList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeInt(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeIntList(IonWriter writer, long[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeIntList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeInt(values2[ii]);
        }
        writer.stepOut();
    }

    public static void writeStringList(IonWriter writer, String[] values2) throws IOException {
        if (writer instanceof _Private_ListWriter) {
            ((_Private_ListWriter)writer).writeStringList(values2);
            return;
        }
        writer.stepIn(IonType.LIST);
        for (int ii = 0; ii < values2.length; ++ii) {
            writer.writeString(values2[ii]);
        }
        writer.stepOut();
    }
}

