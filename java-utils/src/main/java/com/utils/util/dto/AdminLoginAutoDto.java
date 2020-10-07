package com.utils.util.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginAutoDto implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 412656865686654309L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 盐值
     */
    @ApiModelProperty("盐值")
    private String passwordSale;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 状态 1.正常 2.锁定
     */
    @ApiModelProperty("状态 1.正常 2.锁定")
    private Byte state;

    /**
     * 登录次数
     */
    @ApiModelProperty("登录次数")
    private Integer loginCount;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long createTime;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")
    private Long lastLoginTime;

}
