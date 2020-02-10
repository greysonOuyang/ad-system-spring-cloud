package com.greyson.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author greyson
 * @time 2020/2/10 9:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
