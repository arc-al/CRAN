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
 * @since 2022-04-23
 */
@Getter
@Setter
@TableName("typical_business")
@ApiModel(value = "TypicalBusiness对象", description = "")
public class TypicalBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String uplinkBw;

    private String downlinkBw;

    private String eteDelay;

    private String resIsoLevel;

    private String secIsoLevel;

    private String priority;

    private String typicalName;

    private Integer ueNum;


}
