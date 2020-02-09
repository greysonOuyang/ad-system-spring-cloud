package com.greyson.ad.service;

import com.greyson.ad.vo.CreativeRequest;
import com.greyson.ad.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse creatCreative(CreativeRequest request);
}
