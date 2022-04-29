package com.example.cran.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-04-17
 */
@Getter
@Setter
  @TableName("base_station")
@ApiModel(value = "BaseStation对象", description = "")
public class BaseStation implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("基站id")
      @TableId(type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("相对位置X坐标")
      private Double x;

      @ApiModelProperty("相对位置Y坐标")
      private Double y;

      @ApiModelProperty("相对位置Z坐标")
      private Double z;

      @ApiModelProperty("0宏站，1室分")
      private String type;

      @ApiModelProperty("0管城区，1金水区，2二七区")
      private String area;

      @ApiModelProperty("经度")
      private Double longitude;

      @ApiModelProperty("维度")
      private Double latitude;

      @ApiModelProperty("物理小区ID")
      private String pci;

      @ApiModelProperty("是否正在使用")
      private String state;

      @ApiModelProperty("连接终端数量")
      private Integer connTerminalNum;

      @ApiModelProperty("连接MECId")
      private Integer connMecId;

      @ApiModelProperty("传输功率")
      private int transpower;

}
