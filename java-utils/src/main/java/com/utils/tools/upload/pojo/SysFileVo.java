package com.utils.tools.upload.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysFileVo", description = "图片URL")
@Data
public class SysFileVo {

    @ApiModelProperty(value = "ID", name = "id")
    private Long id;

    @ApiModelProperty(value = "文件URL", name = "fileUrl")
    private String fileUrl;

    @ApiModelProperty(value = "缩略图URL", name = "thumbUrl")
    private String thumbUrl;
}
