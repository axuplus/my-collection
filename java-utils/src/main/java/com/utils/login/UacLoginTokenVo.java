package com.utils.login;

import com.utils.util.token.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UacUserVo", description = "用户信息")
public class UacLoginTokenVo implements java.io.Serializable {

    @ApiModelProperty(value = "用户ID", name = "userId")
    private Long userId;

    @ApiModelProperty(value = "登录凭证", name = "token")
    private String token;

    @ApiModelProperty(value = "过期时间", name = "expireIn")
    private Long expireIn;

    @ApiModelProperty(value = "用户类型", name = "userType")
    private Integer userType;

    @ApiModelProperty(value = "状态 0-禁用，1-正常", name = "state")
    private Integer state;

    @ApiModelProperty(value = "用户头像")
    private String headImg;

    public static UacLoginTokenVo buildFromJWT(JwtUtil.JWTInfo jwtInfo, String base64Token, UacUser uacUser){
        UacLoginTokenVo uacLoginTokenVo = new UacLoginTokenVo();
        uacLoginTokenVo.setUserId(uacUser.getId());
        uacLoginTokenVo.setToken(base64Token);
        uacLoginTokenVo.setExpireIn(jwtInfo.getExpireIn());
        uacLoginTokenVo.setUserType(uacUser.getUserType());
        uacLoginTokenVo.setState(uacUser.getState());
        uacLoginTokenVo.setHeadImg(uacUser.getHeadImg());
        return uacLoginTokenVo;
    }
}
