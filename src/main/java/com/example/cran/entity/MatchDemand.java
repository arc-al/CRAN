package com.example.cran.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDemand {
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
