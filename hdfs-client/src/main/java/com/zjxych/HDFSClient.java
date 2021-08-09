package com.zjxych;


import com.sun.corba.se.spi.ior.Writeable;
import com.zjxych.infoKey.infoValue;
import com.zjxych.infoKey.singleBandInfoKey;
import com.zjxych.rasterkyes.SingleBandMapKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;
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

    // public void writeRasterPixel(String path, int level, int row, int column, int band, int rasterValue) throws IOException {
    //     SingleBandMapKey key = new SingleBandMapKey(level,row,column,band);
    //     // BytesWritable key = new BytesWritable(new byte[]{(byte) level, (byte) row, (byte) column, (byte) band});
    //     IntWritable value = new IntWritable(rasterValue);
    //     FileSystem fs = FileSystem.get(conf);
    //     Path filePath = new Path(path);
    //     MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(SingleBandMapKey.class), MapFile.Writer.valueClass(IntWritable.class));
    //         writer.append(key, value);
    //     writer.close();
    //     fs.close();
    // }

    public void writeRaster(String path, int[] rasterValues) throws IOException {
        SingleBandMapKey key = new SingleBandMapKey();
        // BytesWritable key = new BytesWritable(new byte[]{(byte) level, (byte) row, (byte) column, (byte) band});
        IntWritable value = new IntWritable();
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(SingleBandMapKey.class), MapFile.Writer.valueClass(IntWritable.class));
        for (int i = 0; i < rasterValues.length; i++) {
            key.set(0, 0, i, 0);
            value.set(rasterValues[i]);
            writer.append(key, value);
        }
        // writer.append(key, value);
        writer.close();
        fs.close();
    }
    public void writeRasterInfo(String path,String[] rasterInfo) throws IOException{
        singleBandInfoKey key = new singleBandInfoKey();
        infoValue value = new infoValue(rasterInfo);
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        MapFile.Writer writer = new MapFile.Writer(conf,filePath,MapFile.Writer.keyClass(singleBandInfoKey.class),MapFile.Writer.valueClass(String.class));
        key.set(1,1);
        writer.append(key,value);
        writer.close();
        fs.close();

    }
    public int readRasterPixel(String path, int level, int row, int column, int band) throws IOException {
        WritableComparable key = new SingleBandMapKey(level, row, column, band);
        // BytesWritable key = new BytesWritable(new byte[]{(byte) level, (byte) row, (byte) column, (byte) band});
        IntWritable value = new IntWritable();
        FileSystem fs = FileSystem.get(conf);
        MapFile.Reader reader = null;
        reader = new MapFile.Reader(fs, path.toString(), conf);
        reader.get(key, value);
        reader.close();
        fs.close();
        return value.get();
    }

    public void readRaster(String path) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        MapFile.Reader reader = null;
        reader = new MapFile.Reader(fs, path.toString(), conf);
        reader.reset();
        Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass().asSubclass(WritableComparable.class), conf);
        Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass().asSubclass(Writable.class), conf);
        while (reader.next((WritableComparable) key, value)) {
            System.out.println("key:" + key);
            System.out.println("value:" + value);
        }
    }

    public void downloadFile() {
    }

    public void deleteMapFile(String name) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        MapFile.delete(fs, name);
    }
}
