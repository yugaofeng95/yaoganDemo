package com.zjxych.infoKey;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.gdal.gdal.Dataset;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class infoValue extends IntWritable{
    public String[] value;

    public infoValue() {
    }

    public infoValue(String[] value) {
        this.value = value;
    }
    public void setValue(Dataset dataset){

        value[0] = String.valueOf(dataset.getRasterXSize());
        value[1] = String.valueOf(dataset.getRasterYSize());
        value[2] = String.valueOf(dataset.getRasterCount());
        value[3] = String.valueOf(dataset.GetLayerCount());
        value[4] = String.valueOf(dataset.GetRasterBand(1).getDataType());
        value[5] = String.valueOf(dataset.GetGeoTransform()[0]);
        value[6] = String.valueOf(dataset.GetGeoTransform()[1]);
        value[7] = String.valueOf(dataset.GetGeoTransform()[2]);
        value[8] = String.valueOf(dataset.GetGeoTransform()[3]);
        value[9] = String.valueOf(dataset.GetGeoTransform()[4]);
        value[10] = String.valueOf(dataset.GetGeoTransform()[5]);
        value[11] = dataset.GetProjection();

    }

    public String[] getValue() {
        return value;
    }
}