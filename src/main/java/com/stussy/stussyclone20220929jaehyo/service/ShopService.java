package com.stussy.stussyclone20220929jaehyo.service;

import com.stussy.stussyclone20220929jaehyo.dto.shop.CollectionListRespDto;
import com.stussy.stussyclone20220929jaehyo.dto.shop.ProductDetailResDto;

import java.util.List;

public interface ShopService {
    public List<CollectionListRespDto> getCollections(String category, int page) throws Exception;
    public ProductDetailResDto getProductDetails(int groupId) throws Exception;
}
