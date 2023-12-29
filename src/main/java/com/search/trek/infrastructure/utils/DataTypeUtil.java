package com.search.trek.infrastructure.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * The type Data type util.
 *
 * @author wangwei
 * @version 1.0
 */
public class DataTypeUtil {
    /**
     * Float array to byte array byte [ ].
     *
     * @param floats the floats
     * @return the byte [ ]
     */
    public static byte[] floatArrayToByteArray(List<Float> floats) {
        float[] arrayFloats = new float[floats.size()];
        for (int i = 0; i < floats.size(); i++) {
            arrayFloats[i] = floats.get(i);
        }
        ByteBuffer buffer = ByteBuffer.allocate(4 * arrayFloats.length);
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        floatBuffer.put(arrayFloats);
        return buffer.array();
    }

    /**
     * Byte array to float array float [ ].
     *
     * @param bytes the bytes
     * @return the float [ ]
     */
    public static float[] byteArrayToFloatArray(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        FloatBuffer fb = buffer.asFloatBuffer();
        float[] floatArray = new float[fb.limit()];
        fb.get(floatArray);
        return floatArray;
    }

}
