package com.java.com.tools.location.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CountPriceDto implements java.io.Serializable {
    @ApiModelProperty("起始地")
    private String from;
    @ApiModelProperty("目的地")
    private String to;
}
