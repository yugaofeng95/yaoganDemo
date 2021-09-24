package com.zjxych.metainfo;

import com.zjxych.utils.BytesUtil;
import com.zjxych.utils.StringUtil;
import org.gdal.gdal.Dataset;

import java.util.HashMap;
import java.util.Map;

public class Metainfo {
    private String XSizeKey = "resolutionX";
    private String YSzieKey = "resolutionY";
    private String bandskey = "bands";
    private String topLeftXKey = "topLeftX";
    private String topLeftYKey = "topLeftY";
    private String wePixelResolutionKey = "wePixelResolution";
    private String northRotationKey = "northRotation";
    private String southRotationKey = "southRotation";
    private String nsPixelResolutionKey = "nsPixelResolution";
    private String projectionKey = "projection";

    public int bands=0;//波段数
    public double XSize=0; //X方向像素值
    public double YSize=0;//y方向像素值
    public double topLeftX=0;//左上角X坐标
    public double topLeftY=0;//左上角Y坐标
    public double wePixelResolution=0;//东西方向上的像素分辨率
    public double northRotation=0;//北面朝上时地图的旋转角度
    public double southRotation=0;//南面朝上时地图的旋转角度
    public double nsPixelResolution=0;//南北方向上的像素分辨率
    public String projection=null;//投影


    public Metainfo(Dataset dataset) {

        this.bands = dataset.getRasterCount();
        this.XSize = dataset.getRasterXSize();
        this.YSize = dataset.getRasterYSize();
        this.topLeftX = dataset.GetGeoTransform()[0];
        this.wePixelResolution = dataset.GetGeoTransform()[1];
        this.northRotation = dataset.GetGeoTransform()[2];
        this.topLeftY = dataset.GetGeoTransform()[3];
        this.southRotation = dataset.GetGeoTransform()[4];
        this.nsPixelResolution = dataset.GetGeoTransform()[5];
        this.projection = dataset.GetProjection();

    }

    public Metainfo(Map<String, byte[]> map) {

        this.XSize = BytesUtil.toDouble(map.get(XSizeKey));
        this.YSize = BytesUtil.toDouble(map.get(YSzieKey));
        this.bands = BytesUtil.toInt(map.get(bandskey));
        this.topLeftX = BytesUtil.toDouble(map.get(topLeftXKey));
        this.wePixelResolution = BytesUtil.toDouble(map.get(wePixelResolutionKey));
        this.northRotation = BytesUtil.toDouble(map.get(northRotationKey));
        this.topLeftY = BytesUtil.toDouble(map.get(topLeftYKey));
        this.southRotation = BytesUtil.toDouble(map.get(southRotationKey));
        this.nsPixelResolution = BytesUtil.toDouble(map.get(nsPixelResolutionKey));
        this.projection = BytesUtil.toString(map.get(projectionKey));
    }

    public Map<String, byte[]> getWriteMap() {
        Map<String, byte[]> writeMap = new HashMap<>();

        writeMap.put(XSizeKey, BytesUtil.fromDouble(XSize));
        writeMap.put(YSzieKey,BytesUtil.fromDouble(YSize));
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
    public void show(){
        Map<String,String> showMap = new HashMap<>();

        showMap.put(XSizeKey, StringUtil.fromDouble(XSize));
        showMap.put(YSzieKey, StringUtil.fromDouble(YSize));
        showMap.put(bandskey, ""+bands);
        showMap.put(topLeftYKey,StringUtil.fromDouble(topLeftX));
        showMap.put(wePixelResolutionKey,StringUtil.fromDouble(wePixelResolution));
        showMap.put(northRotationKey,StringUtil.fromDouble(northRotation));
        showMap.put(topLeftYKey,StringUtil.fromDouble(topLeftY));
        showMap.put(southRotationKey,StringUtil.fromDouble(southRotation));
        showMap.put(nsPixelResolutionKey,StringUtil.fromDouble(nsPixelResolution));
        showMap.put(projectionKey,projection);
        System.out.println(showMap);
    }
}
