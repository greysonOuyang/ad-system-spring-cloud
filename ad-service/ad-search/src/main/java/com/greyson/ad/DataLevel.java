package com.greyson.ad;

import lombok.Getter;
//方便投递过程中 增量数据索引 的处理
@Getter
public enum DataLevel {

    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 4"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
