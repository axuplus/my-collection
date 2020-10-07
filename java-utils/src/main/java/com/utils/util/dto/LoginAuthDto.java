package com.utils.util.dto;

import com.utils.login.UacUser;
import com.utils.util.token.JwtUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginAuthDto implements Serializable {

    private static final long serialVersionUID = -1137852221455042256L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 类型
     * */
    private Integer type;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 经度
     * */
    private Double lng;
    /**
     * 纬度
     * */
    private Double lat;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;

    public static LoginAuthDto buildFromJWT(JwtUtil.JWTInfo jwtInfo, String base64Token, UacUser uacUser){
        LoginAuthDto loginAuthDto = new LoginAuthDto();
        loginAuthDto.setUserId(uacUser.getId());
        loginAuthDto.setToken(base64Token);
        loginAuthDto.setExpireTime(jwtInfo.getExpireIn());
        return loginAuthDto;
    }




}
