package com.zjxych;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
        client.writeRaster("/testRR", new int[]{16, 65, 83});
        // client.writeRasterPixel("/testRR", 0, 0, 0, 0, 23);
        // client.writeRasterPixel("/testRR", 0, 0, 1, 0, 65);
        // client.writeRasterPixel("/testRR", 0, 0, 2, 0, 83);
        System.out.println(client.readRasterPixel("/testRR", 0, 0, 0, 0));
        System.out.println(client.readRasterPixel("/testRR", 0, 0, 1, 0));
        System.out.println(client.readRasterPixel("/testRR", 0, 0, 2, 0));
        client.readRaster("/testRR");
//        client.WriteFile("/user/hadoop/test1","test8-5");
//         client.WriteMapFile("/user/hadoop/500","keyTest2","6666");
//        client.deleteMapFile("12345");
//           client.readMapFile("/user/hadoop/500");
//        for (LocatedFileStatus fileStatus : client.ListFiles("/")) {
//            System.out.println(fileStatus.getPath());
//        }
//        System.out.println();
    }
}
