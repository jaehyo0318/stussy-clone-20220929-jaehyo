package com.stussy.stussyclone20220929jaehyo.dto.CMResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CMRespDto<T> {
    private int code;
    private String msg;
    private T data;
}
