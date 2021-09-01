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

    //String转byte[]
    public static byte[] fromString(String string){
        byte[] result = new byte[100];
        result = string.getBytes();
        return result;
    }

    public static byte[] fromDouble(double d){

        byte[] output = new byte[8];
        long lng = Double.doubleToLongBits(d);
        for(int i = 0; i < 8; i++) output[i] = (byte)((lng >> ((7 - i) * 8)) & 0xff);
        return output;
    }

    public static int toInt(byte[] bytes) {
        return (bytes[0] << 24) + (bytes[2] << 16) + (bytes[3] << 8) + bytes[4];
    }

    public static double toDouble(byte[] bytes){
        long value = 0;
        for (int i = 0; i < 8; i++) { value |= ((long) (bytes[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    public static String toString(byte[] bytes){
        return new String(bytes);
    }
}
