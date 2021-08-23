package com.zjxych.metainfo;

import com.zjxych.HDFSClient;
import com.zjxych.utils.BytesUtil;
import org.gdal.gdal.Dataset;

import java.util.HashMap;
import java.util.Map;

public class Metainfo {
    private final String resolutionXKey = "resolutionX";
    private final String resolutionYKey = "resolutionY";
    private final String bandskey = "bands";
    private final String topLeftXKey = "topLeftX";
    private final String topLeftYKey = "topLeftY";
    private final String wePixelResolutionKey = "wePixelResolution";
    private final String northRotationKey = "northRotation";
    private final String southRotationKey = "southRotation";
    private final String nsPixelResolutionKey = "nsPixelResolution";
    private final String projectionKey = "projection";
    private int bands;//波段数
    private int resolutionX; //X方向像素值
    private int resolutionY;//y方向像素值
    private double topLeftX;//左上角X坐标
    private double topLeftY;//左上角Y坐标
    private double wePixelResolution;//东西方向上的像素分辨率
    private double northRotation;//北面朝上时地图的旋转角度
    private double southRotation;//南面朝上时地图的旋转角度
    private double nsPixelResolution;//南北方向上的像素分辨率
    private String projection;//投影


    public Metainfo(Dataset dataset) {

        this.resolutionX = dataset.getRasterXSize();
        this.resolutionY = dataset.getRasterYSize();
        this.bands = dataset.getRasterCount();
        this.topLeftX = dataset.GetGeoTransform()[0];
        this.wePixelResolution = dataset.GetGeoTransform()[1];
        this.northRotation = dataset.GetGeoTransform()[2];
        this.topLeftY = dataset.GetGeoTransform()[3];
        this.southRotation = dataset.GetGeoTransform()[4];
        this.nsPixelResolution = dataset.GetGeoTransform()[5];
        this.projection = dataset.GetProjection();

    }

    public Metainfo(Map<String, byte[]> map) {

        this.resolutionX = BytesUtil.toInt(map.get(resolutionXKey));
        this.resolutionY = BytesUtil.toInt(map.get(resolutionYKey));
        this.bands = BytesUtil.toInt(map.get(bandskey));
        this.topLeftX = BytesUtil.toInt(map.get(topLeftXKey));
        this.wePixelResolution = BytesUtil.toInt(map.get(wePixelResolutionKey));
        this.northRotation = BytesUtil.toInt(map.get(northRotationKey));
        this.topLeftY = BytesUtil.toInt(map.get(topLeftYKey));
        this.southRotation = BytesUtil.toInt(map.get(southRotationKey));
        this.nsPixelResolution = BytesUtil.toInt(map.get(nsPixelResolutionKey));
        this.projection = BytesUtil.toString(map.get(projectionKey));
    }

    public Map<String, byte[]> getWriteMap() {
        Map<String, byte[]> writeMap = new HashMap<>();
        writeMap.put(resolutionXKey, BytesUtil.fromInt(resolutionX));
        writeMap.put(resolutionYKey,BytesUtil.fromInt(resolutionY));
        writeMap.put(bandskey,BytesUtil.fromInt(bands));
        writeMap.put(topLeftXKey,BytesUtil.fromDouble(topLeftX));
        writeMap.put(wePixelResolutionKey,BytesUtil.fromDouble(wePixelResolution));
        writeMap.put(northRotationKey,BytesUtil.fromDouble(northRotation));
        writeMap.put(topLeftYKey,BytesUtil.fromDouble(topLeftY));
        writeMap.put(southRotationKey,BytesUtil.fromDouble(southRotation));
        writeMap.put(nsPixelResolutionKey,BytesUtil.fromDouble(nsPixelResolution));
        writeMap.put(projectionKey,BytesUtil.fromString(projection));
        return writeMap;
    }
}
