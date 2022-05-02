package com.example.cran.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
@Getter
@Setter
  @ApiModel(value = "Qiepianthroughput对象", description = "")
public class Qiepianthroughput implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double qiepian1;

    private Double qiepian2;

    private Double qiepian3;

    private Double qiepian4;

    private Double qiepian5;

    private Double qiepian6;

    private Double qiepian7;

    private Double qiepian8;


}
