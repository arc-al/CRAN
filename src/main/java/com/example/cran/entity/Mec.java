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
 * @since 2022-04-18
 */
@Getter
@Setter
  @ApiModel(value = "Mec对象", description = "")
public class Mec implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private Double longitude;

    private Double latitude;

    private String state;

    private Integer connSlicing;

      @ApiModelProperty("是否可被共享")
      private String share;

      @ApiModelProperty("所属区域")
      private String area;


}
