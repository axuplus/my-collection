package com.utils.tools.location.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "LocationDto", description = "位置信息")
@Data
public class LocationDto implements java.io.Serializable {

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度", name = "lng", required = true)
    private Double lng;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度", name = "lat", required = true)
    private Double lat;
}
