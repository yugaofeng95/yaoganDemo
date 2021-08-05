package com.zjxych;

import org.apache.hadoop.fs.LocatedFileStatus;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);
//        client.WriteFile("/user/hadoop/test1","test8-5");
        client.WriteMapFile("/user/hadoop/500","keyTest2","6666");
//        client.deleteMapFile("12345");
          client.readMapFile("/user/hadoop/500");
//        for (LocatedFileStatus fileStatus : client.ListFiles("/")) {
//            System.out.println(fileStatus.getPath());
//        }
//        System.out.println();
    }
}
