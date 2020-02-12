package com.greyson.ad.search;


import com.greyson.ad.search.vo.SearchRequest;
import com.greyson.ad.search.vo.SearchResponse;

//广告位检索请求
public interface ISearch {
    SearchResponse fetchAds(SearchRequest searchRequest);
}
