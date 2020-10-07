package com.utils.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "uac_user")
@ApiModel("司机主表")
public class UacUser implements java.io.Serializable {

    @Id
    private Long id;
    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    private String userNo;
    /**
     * 号码
     */
    @ApiModelProperty("号码")
    private String phone;
    /**
     * 身份证
     */
    @ApiModelProperty("身份证")
    private String idCard;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 状态 0：禁用，1：正常
     */
    @ApiModelProperty("状态 0：禁用，1：正常")
    private Integer state;
    /**
     * 类型 1：自营，2：挂靠
     */
    @ApiModelProperty("类型 1：自营，2：挂靠")
    private Integer userType;
    /**
     * 来源 1：android，2：ios，3：pc
     */
    @ApiModelProperty("来源 1：android，2：ios，3：pc")
    private Integer fromType;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String realName;
    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private Long birthday;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headImg;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String addressCode;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String addressMsg;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long createTime;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;


    /**
     * 车队主键
     */
    @ApiModelProperty("车队主键")
    private Long motorcadeId;


    /**
     * 车队名称
     */
    @ApiModelProperty("车队名称")
    private String motorcadeName;


    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long createBy;


    @ApiModelProperty("积分")
    private Integer source;

    @ApiModelProperty("紧急联系人")
    private String emergency;


    @ApiModelProperty("公司名称")
    private String comName;

    @ApiModelProperty("微信openId")
    private String openId;
}
