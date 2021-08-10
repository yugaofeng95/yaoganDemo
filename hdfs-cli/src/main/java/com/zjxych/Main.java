package com.zjxych;

import java.io.*;
import java.util.Arrays;

import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

public class Main {

    public static void main(String[] args) throws IOException {
//        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
//        client.writeRaster("/testRR", new int[]{16, 65, 83});
//        System.out.println(client.readRasterPixel("/testRR", 0, 0, 0, 0));
//        System.out.println(client.readRasterPixel("/testRR", 0, 0, 1, 0));
//        System.out.println(client.readRasterPixel("/testRR", 0, 0, 2, 0));
//        client.readRaster("/testRR");
        //storage info:
//        String[] arr = {"ss","ss"};
//        client.writeRasterInfo("teat8-9",arr);
//        client.writeRasterInfo("test89");

        //readTiff
        gdal.AllRegister();

//        String rasterFilePath = "\\\\192.168.2.2\\GISData\\1800_dlgh\\zj_dem.tif";
        String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";

        Dataset dataset = gdal.Open(rasterFilePath);
        if (dataset == null) {
            System.out.println("GDAL read error: " + gdal.GetLastErrorMsg());
        }
        Driver driver = dataset.GetDriver();
        System.out.println("Driver: " + driver.getShortName() + "/" + driver.getLongName());

        // 读取影像信息
        int xSize = dataset.getRasterXSize();
        int ySzie = dataset.getRasterYSize();
        int nBandCount = dataset.getRasterCount();
        System.out.println("xSize is " + xSize + ",\nySize is" + ySzie + ",\nbandscount is " + nBandCount);
        int layercount = dataset.GetLayerCount();
        System.out.println("layercount is "+layercount);
        Band band = dataset.GetRasterBand(1);
        int type = band.GetRasterDataType();
        System.out.println("type is "+type);
        double[] transform = dataset.GetGeoTransform();
        System.out.println("top left x:"+transform[0]);
        System.out.println("w--e pixel resolution:"+transform[1]);
        System.out.println("rotation:"+transform[2]);
        System.out.println("top left y:"+transform[3]);
        System.out.println("rotation:"+transform[4]);
        System.out.println("n--s pixel resolution:"+transform[5]);
        dataset.delete();

        gdal.GDALDestroyDriverManager();
        // client.writeRasterPixel("/testRR", 0, 0, 0, 0, 23);
        // client.writeRasterPixel("/testRR", 0, 0, 1, 0, 65);
        // client.writeRasterPixel("/testRR", 0, 0, 2, 0, 83);
//        client.WriteFile("/user/hadoop/test1","test8-5");
//         client.WriteMapFile("/user/hadoop/500","keyTest2","6666");
//        client.deleteMapFile("12345");
//           client.readMapFile("/user/hadoop/500");
//        for (LocatedFileStatus fileStatus : client.ListFiles("/")) {
//            System.out.println(fileStatus.getPath());
//        }
    }
}
