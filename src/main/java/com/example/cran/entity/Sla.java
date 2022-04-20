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
 * @since 2022-04-19
 */
@Getter
@Setter
  @ApiModel(value = "Sla对象", description = "")
public class Sla implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String type;

    private String uplinkBw;

    private String downlinkBw;

    private String eteDelay;

    private String resIsoLevel;

    private String secIsoLevel;

    private String pirority;

    private Integer connSlicNum;

    private String message;


}
