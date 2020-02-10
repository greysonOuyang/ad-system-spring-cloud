package com.greyson.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author greyson
 * @time 2020/2/10 9:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitInterestTable {

    private Long unitId;
    private String interestTag;
}
