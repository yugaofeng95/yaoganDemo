package com.zjxych.infoKey;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class singleBandInfoKey extends BinaryComparable implements WritableComparable<BinaryComparable> {
    public int level;
//    private int band;
//    private String[] rasterInfo;
//    private String[] bandInfo;

    public singleBandInfoKey() {
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public singleBandInfoKey(int level) {
        this.level = level;
//        this.band = band;
//        this.rasterInfo = rasterInfo;
//        this.bandInfo = bandInfo;
    }
    public void set(int level,int band){

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }
}
