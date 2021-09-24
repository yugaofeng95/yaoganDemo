package com.zjxych;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.zjxych.metainfo.Metainfo;
import com.zjxych.metainfo.MetainfoValue;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.apache.hadoop.fs.Path;
import org.gdal.gdalconst.gdalconst;


public class Main {

    public static void main(String[] args) throws IOException {
//        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
        // client.writeRaster("/testRR", new int[]{16, 65, 83});
        // System.out.println(client.readRasterPixel("/testRR", 0, 0, 0, 0));
        // System.out.println(client.readRasterPixel("/testRR", 0, 0, 1, 0));
        // System.out.println(client.readRasterPixel("/testRR", 0, 0, 2, 0));
        // client.readRaster("/testRR");


        //readTiff
//        {
//            gdal.AllRegister();
//
//            String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";
//
//            Dataset dataset = gdal.Open(rasterFilePath);
//            if (dataset == null) {
//                System.out.println("GDAL read error: " + gdal.GetLastErrorMsg());
//            }
//            assert dataset != null;
//            Driver driver = dataset.GetDriver();
//            System.out.println("Driver: " + driver.getShortName() + "/" + driver.getLongName());
//



        // client.writeRasterPixel("/testRR", 0, 0, 0, 0, 23);
        // client.writeRasterPixel("/testRR", 0, 0, 1, 0, 65);
        // client.writeRasterPixel("/testRR", 0, 0, 2, 0, 83);


        //写入影像元数据
//        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
//        gdal.AllRegister();
//        String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";
//        Dataset dataset = gdal.Open(rasterFilePath);
//        client.writeMetainfo("test09", dataset);


//        读取影像元数据
        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
        gdal.AllRegister();
        gdal.SetConfigOption("gdal_FILENAME_IS_UTF8", "YES");

        String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";
        Dataset dataset = gdal.Open(rasterFilePath);
        client.readInfo("test09");
        System.out.println(dataset.getRasterXSize());
        System.out.println(dataset.getRasterCount());


        //写入影像数据
//        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
//        gdal.AllRegister();
//        String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";
//        Dataset dataset = gdal.Open(rasterFilePath);
////        client.writerImage("test07",dataset);
//        Band band = dataset.GetRasterBand(1);
//        int xSize = dataset.getRasterXSize();
//        int ySize = dataset.getRasterYSize();
//        int[] rasterValues = new int[xSize*ySize];
//
//
//        band.ReadRaster(0,0,xSize,ySize,rasterValues);
//        for (int i=0; i<10;i++){
//            System.out.println(rasterValues[i]);
//        }
//
//


    }
}
