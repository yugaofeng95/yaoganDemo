package com.zjxych.metainfo;

import com.nimbusds.jose.util.ArrayUtils;
import com.zjxych.utils.BytesUtil;
import org.apache.hadoop.io.Writable;
import org.gdal.gdal.Dataset;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class MetainfoValue implements Writable {
    public String[] value =new String[100];
    public byte[] valuebyte = new byte[100];
    private int cols;
    private int rows;
    private int bands;
    private int topLeftX;
    private int wePixelResolution;
    private int rotation;
    private int topleftY;
    private int rotation2;
    private int nsPixelResolution;


    public MetainfoValue() {
    }

    public MetainfoValue(int cols, int rows, int bands, int topLeftX, int wePixelResolution, int rotation, int topleftY, int rotation2, int nsPixelResolution) {
        this.cols = cols;
        this.rows = rows;
        this.bands = bands;
        this.topLeftX = topLeftX;
        this.wePixelResolution = wePixelResolution;
        this.rotation = rotation;
        this.topleftY = topleftY;
        this.rotation2 = rotation2;
        this.nsPixelResolution = nsPixelResolution;
    }

    public MetainfoValue(String[] value) {
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
    //待补充
    public void set(byte[] bytes) throws IOException{
        valuebyte = bytes;
        cols = valuebyte[0];
//        rows = valuebyte[];

    }


    public String[] getValue() {
        return value;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        int j=0;
        for(int i=0; i <=value.length;i++){
            byte[] trans = BytesUtil.fromString(value[i]);
            j+=trans.length;
            System.arraycopy(trans,0,valuebyte,j,trans.length);
        }
        dataOutput.write(valuebyte,0,value.length);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        dataInput.readFully(valuebyte);
        set(valuebyte);
    }


    @Override
    public String toString() {
        return "MetainfoValue{" +
                "cols=" + cols +
                ", rows=" + rows +
                ", bands=" + bands +
                ", topLeftX=" + topLeftX +
                ", wePixelResolution=" + wePixelResolution +
                ", rotation=" + rotation +
                ", topleftY=" + topleftY +
                ", rotation2=" + rotation2 +
                ", nsPixelResolution=" + nsPixelResolution +
                '}';
    }
}
