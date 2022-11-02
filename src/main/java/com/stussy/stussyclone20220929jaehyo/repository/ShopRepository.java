package com.stussy.stussyclone20220929jaehyo.repository;

import com.stussy.stussyclone20220929jaehyo.domain.CollectionProduct;
import com.stussy.stussyclone20220929jaehyo.domain.Product;
import com.stussy.stussyclone20220929jaehyo.domain.ProductDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopRepository {
    public List<CollectionProduct> getCollectionList(Map<String, Object> map) throws  Exception;
    public List<ProductDetail> getProductList(int groupId) throws Exception;

}
