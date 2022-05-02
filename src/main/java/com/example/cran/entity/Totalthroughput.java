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
  @ApiModel(value = "Totalthroughput对象", description = "")
public class Totalthroughput implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer tti;

    private Double totalUlThroughput;

    private Double totalDlThroughput;


}
