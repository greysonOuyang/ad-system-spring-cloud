package com.greyson.ad.constant;

/**
 * @author greyson
 * @time 2020/2/7 20:59
 */

public enum CreativeType {
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
