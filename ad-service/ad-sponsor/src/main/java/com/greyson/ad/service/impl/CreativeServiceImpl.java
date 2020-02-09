package com.greyson.ad.service.impl;

import com.greyson.ad.dao.CreativeRepository;
import com.greyson.ad.entity.Creative;
import com.greyson.ad.service.ICreativeService;
import com.greyson.ad.vo.CreativeRequest;
import com.greyson.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author greyson
 * @time 2020/2/8 13:46
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private  final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse creatCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(
                request.convertToEntity()
        );
        return null;
    }
}
