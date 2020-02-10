package com.greyson.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author greyson
 * @time 2020/2/10 9:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictTable {
    private Long unitId;
    private String province;
    private String city;
}
