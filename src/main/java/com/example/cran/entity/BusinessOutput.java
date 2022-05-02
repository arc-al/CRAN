package com.example.cran.entity;

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
 * @since 2022-05-02
 */
@Getter
@Setter
  @TableName("business_output")
@ApiModel(value = "BusinessOutput对象", description = "")
public class BusinessOutput implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private Integer tti;

    private Double businessOutput;


}
