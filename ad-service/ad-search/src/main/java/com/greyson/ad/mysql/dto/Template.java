package com.greyson.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author greyson
 * @time 2020/2/10 17:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;
}
