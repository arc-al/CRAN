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
  @ApiModel(value = "Delay对象", description = "")
public class Delay implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double delay1;

    private Double delay2;

    private Double delay3;

    private Double delay4;

    private Double delay5;

    private Double delay6;

    private Double delay7;

    private Double delay8;


}
