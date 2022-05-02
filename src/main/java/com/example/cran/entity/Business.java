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
  @ApiModel(value = "Business对象", description = "")
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double business1;

    private Double business2;

    private Double business3;

    private Double business4;

    private Double business5;

    private Double business6;

    private Double business7;

    private Double business8;


}
