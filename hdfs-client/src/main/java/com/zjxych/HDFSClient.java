package com.zjxych;


import com.zjxych.metainfo.MetainfoValue;
import com.zjxych.metainfo.Metainfo;
import com.zjxych.rasterkyes.SingleBandMapKey;
import com.zjxych.utils.sort;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.gdal.gdal.Dataset;

import java.io.*;
import java.util.*;

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

    //写入元数据
//    public void writerInfo(String path, MetainfoValue value, SingleBandMapKey key, Dataset dataset) throws IOException {
//        FileSystem fs = FileSystem.get(conf);
//        Path filePath = new Path(path);
//        MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(SingleBandMapKey.class), MapFile.Writer.valueClass(MetainfoValue.class));
//        value.setValue(dataset);
//        for (int i = 0; i < 12; i++) {
//            System.out.println(value.getValue()[i]);
//        }
//        writer.append(key, value);
//        writer.close();
//        fs.close();
//    }

    public void writeMetainfo(String path, Dataset dataset) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        Metainfo metainfoWriter = new Metainfo(dataset);
        Map<String,byte[]> writerMap = metainfoWriter.getWriteMap();
        Map<String,byte[]> resultMap = sort.sortMap(writerMap);
        Text key = new Text();
        BytesWritable value = new BytesWritable();
        MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(Text.class), MapFile.Writer.valueClass(BytesWritable.class));
        for (Map.Entry<String, byte[]> pair : resultMap.entrySet()) {
            key.set(pair.getKey());
            value.set(pair.getValue(), 0, pair.getValue().length);
            writer.append(key, value);
        }
//        Set<Map.Entry<String,byte[]>> entrySet = writerMap.entrySet();
//        Iterator<Map.Entry<String,byte[]>> it = entrySet.iterator();
//        while(it.hasNext()){
//            Map.Entry<String,byte[]> entry = it.next();
//            key.set(entry.getKey());
//            value.set(entry.getValue(),0,entry.getValue().length);
//            writer.append(key,value);
//        }

//        writer.append(key, value);
        writer.close();
        fs.close();
    }

    //读取元数据
    public Metainfo readInfo(Path dir) throws IOException {
//        singleBandInfoKey key = new singleBandInfoKey(level);
//        String[] value = new String[100];
        FileSystem fs = FileSystem.get(conf);
        Text key = new Text();
        BytesWritable value = new BytesWritable();
        MapFile.Reader reader = new MapFile.Reader(dir, conf);
        Map<String, byte[]> map = new HashMap<>();
        while (reader.next(key, value)) {
            map.put(key.toString(), value.getBytes());
        }
        Metainfo writer = new Metainfo(map);
        reader.close();
        fs.close();
        return writer;
    }


    //写入栅格数据
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

    //存储tiff图片
    public void writerImage(String path,Dataset dataset) throws IOException{
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
    }

    public void writeRasterInfo(String path, String[] rasterInfo) throws IOException {
        SingleBandMapKey key = new SingleBandMapKey();
        MetainfoValue value = new MetainfoValue(rasterInfo);
        FileSystem fs = FileSystem.get(conf);
        Path filePath = new Path(path);
        MapFile.Writer writer = new MapFile.Writer(conf, filePath, MapFile.Writer.keyClass(SingleBandMapKey.class), MapFile.Writer.valueClass(String.class));
        // key.set(1, 1);
        writer.append(key, value);
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
