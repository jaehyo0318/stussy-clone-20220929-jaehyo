package com.stussy.stussyclone20220929jaehyo.dto.shop;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class ProductDetailResDto {
    private int groupId;
    private String name;
    private int price;
    private String infoSimple;
    private String infoDetail;
    private String infoOption;
    private String infoManagement;
    private String infoShipping;
    private Map<String, List<String>> options;
    private List<String> imgNames;

}
