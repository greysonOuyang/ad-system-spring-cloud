package com.greyson.ad.index.adunit;

import com.greyson.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author greyson
 * @time 2020/2/9 17:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer posiitionType;
    private Long planId;

    private AdPlanObject adPlanObject;

    void update(AdUnitObject newObject) {
        if (null != newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (null != newObject.getPosiitionType()) {
            this.posiitionType = newObject.getPosiitionType();
        }
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }
        if (null != newObject.getAdPlanObject()) {
            this.adPlanObject = newObject.getAdPlanObject();
        }
    }
    private static boolean isKaiPing(int posiitionType) {
        return (posiitionType & AdUnitConstants.POSITION_TYPE.KAIPING) > 0; // 与运算后大于零则匹配
    }

    private static boolean isTiePian(int posiitionType) {
        return (posiitionType & AdUnitConstants.POSITION_TYPE.TIEPIAN) > 0; // 与运算后大于零则匹配
    }

    private static boolean isTiePianMiddle(int posiitionType) {
        return (posiitionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0; // 与运算后大于零则匹配
    }

    private static boolean isTiePianPost(int posiitionType) {
        return (posiitionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0; // 与运算后大于零则匹配
    }

    public static boolean isAdSlotTypeOk(int adSlotType, int posiitionType) {
        switch (adSlotType) {
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(posiitionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiePian(posiitionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(posiitionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(posiitionType);
            default:
                return false;
        }
    }
}
