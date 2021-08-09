package com.zjxych;

import java.io.*;

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

        String rasterFilePath = "\\\\192.168.2.2\\GISData\\1800_dlgh\\zj_dem.tif";

        Dataset dataset;
        dataset = gdal.Open(rasterFilePath,
                gdalconstConstants.GA_ReadOnly);
        if (dataset == null) {
            System.out.println("GDAL read error: " + gdal.GetLastErrorMsg());
        }

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
        System.out.println();
    }
}
