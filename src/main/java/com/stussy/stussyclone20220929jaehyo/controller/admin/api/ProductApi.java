package com.stussy.stussyclone20220929jaehyo.controller.admin.api;

import com.stussy.stussyclone20220929jaehyo.aop.annotation.LogAspect;
import com.stussy.stussyclone20220929jaehyo.aop.annotation.ValidAspect;
import com.stussy.stussyclone20220929jaehyo.dto.CMResponseDto.CMRespDto;
import com.stussy.stussyclone20220929jaehyo.dto.admin.ProductAdditionReqDto;
import com.stussy.stussyclone20220929jaehyo.dto.admin.ProductModificationReqDto;
import com.stussy.stussyclone20220929jaehyo.dto.validation.ValidationSequence;
import com.stussy.stussyclone20220929jaehyo.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220929jaehyo.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @ValidAspect
    @LogAspect
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@Validated(ValidationSequence.class) ProductAdditionReqDto productAdditionReqDto, BindingResult bindingResult) throws Exception {

//        String productName = productAdditionReqDto.getName();
//        for(int i = 0; i < 200; i++) {
//            if(i % 4 == 0) {
//                productAdditionReqDto.setName(productName + "-" + (i + 1));
//
//            }
//           productService.addProduct(productAdditionReqDto);
//        }



        return ResponseEntity
                .created(null)
                .body(new CMRespDto<>(1, "성공", productService.addProduct(productAdditionReqDto)));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProductList(@RequestParam int page,
                                            @RequestParam @Nullable String category,
                                            @RequestParam @Nullable String searchValue
                                            ) throws Exception {



        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.getProductList(page, category, searchValue)));
    }

    @LogAspect
    @Validated
    @PostMapping("/products/modification")
    public ResponseEntity<?> updateProduct(@Valid
                                               ProductModificationReqDto productModificationReqDto,
                                                BindingResult bindingResult) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.updateProduct(productModificationReqDto)));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) throws Exception{

    return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.deleteProduct(productId)));
    }
}
