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
  @TableName("control_parameter")
@ApiModel(value = "ControlParameter对象", description = "")
public class ControlParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer caseNum;

    private Integer caseTti;

    private Integer vsamplingFunction;

    private Integer ttiLength;

    private Integer samplingUnit;

    private Integer simuT;

    private Integer samplingNum;

    private String samplingInterval;

    private Integer tti;

    private Integer serviceType;

    private Integer subcarrierSpacing;

    private Integer dataLength;

    private Double decodingErrorRate;

    private Integer systemMode;

    private Double ulTransRatio;

    private Integer totalTtiNum;


}
