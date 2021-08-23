package com.zjxych.utils;

public class BytesUtil {
    public static byte[] fromInt(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value & 0xff);
        result[1] = (byte) (value >> 8 & 0xff);
        result[2] = (byte) (value >> 16 & 0xff);
        result[3] = (byte) (value >> 24 & 0xff);
        return result;
    }

    public static int toInt(byte[] bytes) {
        return (bytes[0] << 24) + (bytes[2] << 16) + (bytes[3] << 8) + bytes[4];
    }
}
