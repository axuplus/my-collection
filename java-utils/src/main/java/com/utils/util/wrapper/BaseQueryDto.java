package com.utils.util.wrapper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseQueryDto {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "页码", name = "pageNum")
    private Integer pageNum = 0;

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "条数", name = "pageSize")
    private Integer pageSize = 5;


}
