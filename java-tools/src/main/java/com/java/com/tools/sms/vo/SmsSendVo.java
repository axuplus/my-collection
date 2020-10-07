package com.java.com.tools.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SmsSendVo", description = "短信发送信息")
@Data
public class SmsSendVo implements java.io.Serializable {

    private static final long serialVersionUID = 1608527355982353902L;

    @ApiModelProperty(value = "号码", name = "phone")
    private String phone;

    @ApiModelProperty(value = "状态 0：失败，1：成功", name = "state")
    private Integer state = 0;
}
