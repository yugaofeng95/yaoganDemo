package com.zjxych;

import org.apache.hadoop.fs.LocatedFileStatus;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        HDFSClient client = new HDFSClient("hadoop", "192.168.1.183", 9000);

        for (LocatedFileStatus fileStatus : client.ListFiles("/")) {
            System.out.println(fileStatus.getPath());
        }
        System.out.println();
    }
}
