package com.stussy.stussyclone20220929jaehyo.repository.admin;

import com.stussy.stussyclone20220929jaehyo.domain.Product;
import com.stussy.stussyclone20220929jaehyo.domain.ProductImgFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductRepository {

    public int saveProduct(Product product) throws Exception;
    public int saveImgFiles(List<ProductImgFile> product_img_files) throws Exception;

    public List<Product> getProductList(Map<String, Object> map) throws Exception;

    public int setProduct(Product product) throws Exception;

    public int deleteImgFiles(Map<String, Object> map) throws Exception;

    public List<ProductImgFile> getProductImgList(int productId) throws Exception;
    public int deleteProduct(int productId) throws Exception;
}
