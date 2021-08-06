package com.zjxych.rasterkyes;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SingleBandMapKey extends BinaryComparable implements WritableComparable<BinaryComparable> {
    private int Level;
    private long Row;
    private long Column;
    private int Band;

    private byte[] value;

    public SingleBandMapKey() {
        this(0, 0, 0, 0);
    }

    public SingleBandMapKey(int level, int row, int column, int band) {
        value = new byte[10];
        set(level, row, column, band);
    }

    public SingleBandMapKey(byte[] bytes) throws IOException {
        set(bytes);
    }

    @Override
    public int getLength() {
        return value.length;
    }

    @Override
    public byte[] getBytes() {
        return value;
    }

    public void set(int level, int row, int column, int band) {
        this.Level = level;
        this.Row = row;
        this.Column = column;
        this.Band = band;
        value[0] = (byte) Level;
        value[1] = (byte) (Row >> 24);
        value[2] = (byte) ((Row >> 16) % 256);
        value[3] = (byte) ((Row >> 8) % 65536);
        value[4] = (byte) (Band % 16777216);
        value[5] = (byte) (Column >> 24);
        value[6] = (byte) ((Column >> 16) % 256);
        value[7] = (byte) ((Column >> 8) % 65536);
        value[8] = (byte) (Column % 16777216);
        value[9] = (byte) Band;
    }

    public void set(byte[] bytes) throws IOException {
        if (bytes.length != 10) {
            throw new IOException("Raster map file key bytes length must be 10.");
        }
        value = bytes;
        Level = value[0];
        Row = (long) (value[1] << 24) + (value[2] << 16) + (value[3] << 8) + value[4];
        Column = (long) (value[5] << 24) + (value[6] << 16) + (value[7] << 8) + value[8];
        Band = value[9];
    }

    @Override
    public void write(DataOutput out) throws IOException {
        // byte[] bytes = new byte[10];
        // bytes[0] = (byte) Level;
        // bytes[1] = (byte) (Row >> 24);
        // bytes[2] = (byte) ((Row >> 16) % 256);
        // bytes[3] = (byte) ((Row >> 8) % 65536);
        // bytes[4] = (byte) (Band % 16777216);
        // bytes[5] = (byte) (Column >> 24);
        // bytes[6] = (byte) ((Column >> 16) % 256);
        // bytes[7] = (byte) ((Column >> 8) % 65536);
        // bytes[8] = (byte) (Column % 16777216);
        // bytes[9] = (byte) Band;
        out.write(value, 0, 10);
        // out.writeLong(((long) (Level * LevelDivider) + (long) (Row * RowDivider)) * RemainDivider);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        byte[] bytes = new byte[10];
        in.readFully(bytes);
        set(bytes);
    }

    @Override
    public String toString() {
        return String.format("level: %s, row: %s, column: %s, band: %s", Level, Row, Column, Band);
    }
}
