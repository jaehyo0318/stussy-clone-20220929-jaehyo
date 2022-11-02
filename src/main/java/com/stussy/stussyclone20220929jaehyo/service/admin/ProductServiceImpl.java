package com.stussy.stussyclone20220929jaehyo.service.admin;

import com.stussy.stussyclone20220929jaehyo.aop.annotation.LogAspect;
import com.stussy.stussyclone20220929jaehyo.domain.Product;
import com.stussy.stussyclone20220929jaehyo.domain.ProductImgFile;
import com.stussy.stussyclone20220929jaehyo.dto.admin.ProductAdditionReqDto;
import com.stussy.stussyclone20220929jaehyo.dto.admin.ProductListRespDto;
import com.stussy.stussyclone20220929jaehyo.dto.admin.ProductModificationReqDto;
import com.stussy.stussyclone20220929jaehyo.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220929jaehyo.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Value("${file.path}")
    private String filePath;

    private final ProductRepository productRepository;

    @Override
    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception {
        int resultCount = 0;

        List<MultipartFile> files = productAdditionReqDto.getFiles();
        List<ProductImgFile> productImgFiles = null;

        Product product = productAdditionReqDto.toProductEntity();
        resultCount = productRepository.saveProduct(product);

        if(files != null) {
            int productId = product.getId();
            productImgFiles = getProductImgFiles(files, productId);
            resultCount = productRepository.saveImgFiles(productImgFiles);
        }

        if(resultCount == 0){
            throw new CustomInternalServerErrorException("상품 등록 실패");
        }

        return true;
    }

    private List<ProductImgFile> getProductImgFiles(List<MultipartFile> files, int productId) throws Exception{
        List<ProductImgFile> productImgFiles = new ArrayList<ProductImgFile>();

        files.forEach(file -> {
            String origin_name = file.getOriginalFilename();
            String extension = origin_name.substring(origin_name.lastIndexOf("."));
            String temp_name = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get(filePath + "/product/" + temp_name);

            File f = new File(filePath + "/product");

            if(!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.write(uploadPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ProductImgFile productImgFile = ProductImgFile.builder()
                    .product_id(productId)
                    .origin_name(origin_name)
                    .temp_name(temp_name)
                    .build();

            productImgFiles.add(productImgFile);

        });
        return productImgFiles;
    }


    @LogAspect
    @Override
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("index", (pageNumber - 1) * 10);
        paramsMap.put("category", category);
        paramsMap.put("searchText", searchText);

        List<ProductListRespDto> list = new ArrayList<ProductListRespDto>();

        productRepository.getProductList(paramsMap).forEach(product -> {
            list.add(product.toListRespDto());
        });

        return list;
    }

    @Override
    public boolean updateProduct(ProductModificationReqDto productModificationReqDto) throws Exception {
        boolean status = false;

        int result = productRepository.setProduct(productModificationReqDto.toProductEntity());

        if(result != 0) {
            status = true;
            boolean insertStatus = true;
            boolean deleteStatus = true;

            if(productModificationReqDto.getFiles() != null){
                insertStatus = insertProductImg(productModificationReqDto.getFiles(), productModificationReqDto.getId());
            }
            if(productModificationReqDto.getDeleteImgFiles() != null) {
                deleteStatus = deleteProductImg(productModificationReqDto.getDeleteImgFiles(), productModificationReqDto.getId());
            }
            status = status && insertStatus && deleteStatus;

            if(status == false) {
                throw new CustomInternalServerErrorException("상품 수정 오류");
            }
        }

        return false;
    }

    private boolean insertProductImg(List<MultipartFile> files, int productId) throws Exception {
        List<ProductImgFile> productImgFiles = getProductImgFiles(files, productId);

        return productRepository.saveImgFiles(productImgFiles) > 0;
    }

    private boolean deleteProductImg(List<String> deleteImgFiles, int productId) throws Exception {
        boolean status = false;
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("productId", productId);
        map.put("deleteImgFiles", deleteImgFiles);

        int result = productRepository.deleteImgFiles(map);
        if(result != 0) {
            deleteImgFiles.forEach(temp_name -> {
                Path uploadPath = Paths.get(filePath + "/product/" + temp_name);

                File file = new File(uploadPath.toUri());
                if(file.exists()) {
                    file.delete();
                }
            });
            status = true;
        }
        return status;
    }

    @Override
    public boolean deleteProduct(int productId) throws Exception {
        List<ProductImgFile> productImgFiles = productRepository.getProductImgList((productId));

        if(productRepository.deleteProduct(productId) > 0) {
            productImgFiles.forEach(productImgFile -> {
                Path uploadPath = Paths.get(filePath + "/product/" + productImgFile.getTemp_name());

                File file = new File(uploadPath.toUri());
                if(file.exists()) {
                    file.delete();
                }
            });
            return true;
        }

        return false;
    }
}
