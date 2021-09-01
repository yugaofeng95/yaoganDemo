package com.zjxych;

import java.io.*;

import com.zjxych.metainfo.Metainfo;
import com.zjxych.metainfo.MetainfoValue;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.apache.hadoop.fs.Path;


public class Main {

    public static void main(String[] args) throws IOException {
        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
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
//            // 读取影像信息
//            int xSize = dataset.getRasterXSize();
//            int ySzie = dataset.getRasterYSize();
//            int BandCount = dataset.getRasterCount();
//            System.out.println("xSize is " + xSize + ",\nySize is " + ySzie + ",\nbandscount is " + BandCount);
//            int layers = dataset.GetLayerCount();
//            System.out.println("layercount is " + layers);
//            Band band = dataset.GetRasterBand(1);
//            int type = band.GetRasterDataType();
//            System.out.println("type is " + type);
//            double[] transform = dataset.GetGeoTransform();
//            System.out.println("top left x:" + transform[0]);
//            System.out.println("w--e pixel resolution:" + transform[1]);
//            System.out.println("rotation:" + transform[2]);
//            System.out.println("top left y:" + transform[3]);
//            System.out.println("rotation:" + transform[4]);
//            System.out.println("n--s pixel resolution:" + transform[5]);
//            String projection = dataset.GetProjection();
//            System.out.println("projection:" + projection);
//            dataset.delete();
//            gdal.GDALDestroyDriverManager();
//        }


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


        //store tiff data in HDFS

        gdal.AllRegister();
//
        String rasterFilePath = "D:\\romote sense Pro\\zj_dem.tif";
//
        Dataset dataset = gdal.Open(rasterFilePath);
//            HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
//        MetainfoValue infoValue = new MetainfoValue(new String[100]);
        // singleBandInfoKey key = new singleBandInfoKey(0);

        client.writeMetainfo("test05", dataset);

//        Metainfo metainfoWriter = new Metainfo(dataset);
//        System.out.println(metainfoWriter.getWriteMap());

//        Path path = new Path("test04");
//        client.readInfo(path);
    }
}
