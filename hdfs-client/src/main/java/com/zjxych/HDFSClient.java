package com.zjxych;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class HDFSClient {
    private Configuration conf = new Configuration();

    public HDFSClient(String username, String host, Integer port) {
        System.setProperty("HADOOP_USER_NAME", username);
        conf.set("fs.defaultFS", String.format("hdfs://%1s:%2d", host, port));
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
    }

    public List<LocatedFileStatus> ListFiles(String path) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator remoteIterator = fs.listFiles(new Path("/"), true);
        List<LocatedFileStatus> files = new ArrayList<>();
        while (remoteIterator.hasNext()) {
            files.add((LocatedFileStatus) remoteIterator.next());
        }
        fs.close();
        return files;
    }

    public void WriteFile(String path, String content) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        if (fs.exists(filePath)) {
            fs.delete(filePath, true);
        }
        OutputStream outputStream = fs.create(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF8"));
        bufferedWriter.write(content);
        bufferedWriter.close();
        outputStream.close();
        fs.close();
    }

    public void WriteMapFile(String path, String key, String content) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        if (fs.exists(filePath)) {
            fs.delete(filePath, true);
        }
        OutputStream outputStream = fs.create(filePath);
        MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(Text.class), MapFile.Writer.valueClass(Text.class));
        writer.append(new Text(key), new Text(content));
        writer.close();
        fs.close();
    }
}
