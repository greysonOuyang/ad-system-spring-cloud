package com.greyson.ad.constant;

import lombok.Getter;

/**
 * @author greyson
 * @time 2020/2/7 21:03
 */

@Getter
public enum CreativeMaterialType {
    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMaterialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
