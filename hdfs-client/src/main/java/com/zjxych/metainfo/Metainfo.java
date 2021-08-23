package com.zjxych.metainfo;

import com.zjxych.HDFSClient;
import com.zjxych.utils.BytesUtil;
import org.gdal.gdal.Dataset;

import java.util.HashMap;
import java.util.Map;

public class Metainfo {
    private String resolutionXKey = "resolutionX";
    private int resolutionX;

    public Metainfo(Dataset dataset) {
        this.resolutionX = dataset.getRasterXSize();
    }

    public Metainfo(Map<String, byte[]> map) {
        this.resolutionX = BytesUtil.toInt(map.get(resolutionXKey));
    }

    public Map<String, byte[]> getWriteMap() {
        Map<String, byte[]> writeMap = new HashMap<>();
        writeMap.put(resolutionXKey, BytesUtil.fromInt(resolutionX));
        return writeMap;
    }
}
