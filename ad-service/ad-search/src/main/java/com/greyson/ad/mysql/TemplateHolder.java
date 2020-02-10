package com.greyson.ad.mysql;

import com.greyson.ad.mysql.constant.OpType;
import com.greyson.ad.mysql.dto.ParseTemplate;
import com.greyson.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author greyson
 * @time 2020/2/10 20:10
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;
    private String SQL_SCHEMA = "select table_schema, table_name," +
            "column_name, ordinal_position from information_schema.columns" +
            "where table_schema = ? and table_name = ?";
    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry :
                template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> updateFields = table.getOpTypeFilterMap().get(
                    OpType.UPDATE
            );
        }
    }
}
