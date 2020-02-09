package com.greyson.ad.service;

import com.greyson.ad.entity.unit_condition.AdUnitDistrict;
import com.greyson.ad.entity.unit_condition.AdUnitKeyword;
import com.greyson.ad.entity.unit_condition.CreativeUnit;
import com.greyson.ad.exception.AdException;
import com.greyson.ad.vo.*;

/**
 * @author greyson
 * @time 2020/2/8 9:38
 */

public interface IAdUnitService {
    AdUnitResponse createUnit(AdUnitRequest request)
        throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request)
        throws AdException;

    AdUnitInterestResponse createUnitInterest(AdUnitInterestRequest request)
        throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request)
        throws AdException;
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request)
        throws AdException;
}
