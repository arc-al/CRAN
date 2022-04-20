package com.example.cran.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
  @ApiModel(value = "Slicing对象", description = "")
public class Slicing implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String type;

    private Integer connSla;

    private String service;

    private String message;

    private String state;


}
