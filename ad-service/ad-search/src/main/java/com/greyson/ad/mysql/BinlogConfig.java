package com.greyson.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "adconf.mysql")
public class BinlogConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;

}
