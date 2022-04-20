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
  @ApiModel(value = "Terminal对象", description = "")
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("UE的id")
        private Integer id;

      @ApiModelProperty("经度")
      private Double longitude;

      @ApiModelProperty("纬度")
      private Double latitude;

      @ApiModelProperty("业务类型")
      private String businessType;

      @ApiModelProperty("关联的切片id")
      private Integer connSlicing;

      @ApiModelProperty("关联的基站id")
      private Integer connBs;


}
