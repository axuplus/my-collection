package com.utils.login;

import com.utils.util.constant.GlobalConstant;
import com.utils.util.date.DateUtil;
import com.utils.util.dto.LoginAuthDto;
import com.utils.util.token.JwtUtil;
import com.utils.util.token.TokenObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UacLoginTokenServiceImpl implements UacLoginTokenService {

    protected final Logger logger = LoggerFactory.getLogger(UacLoginTokenServiceImpl.class);


    @Value(value = "${token.secretKey}")
    private String token_secret_key;

    @Value(value = "${token.ttl}")
    private Integer token_ttl;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public UacLoginTokenVo generateToken(UacUser uacUser) {
        final Date nowDate = new Date();
        final Date expiresIn = DateUtil.addDay(nowDate, token_ttl);
        final JwtUtil.JWTInfo jwtInfo = new JwtUtil().createJWT(nowDate, expiresIn, new TokenObject(token_secret_key, uacUser.getId()));

        String token = Base64.encodeBase64URLSafeString(jwtInfo.getToken().getBytes());
        LoginAuthDto loginAuthDto = LoginAuthDto.buildFromJWT(jwtInfo, token, uacUser);
//        loginAuthDto.setUserType(uacUser.getUserType());
//        loginAuthDto.setPhone(uacUser.getPhone());
//        loginAuthDto.setNickName(uacUser.getNickName());
//        loginAuthDto.setMotorcadeId(uacUser.getMotorcadeId());
//        loginAuthDto.setUserName(uacUser.getRealName() == null ? uacUser.getPhone() : uacUser.getRealName());
//        loginAuthDto.setHeadImg(uacUser.getHeadImg());
//        loginAuthDto.setMotorcadeName(uacUser.getMotorcadeName());
        UacLoginTokenVo uacLoginTokenVo = UacLoginTokenVo.buildFromJWT(jwtInfo, token, uacUser);

        redisTemplate.opsForHash().delete(GlobalConstant.LOGIN_TOKEN, uacUser.getId());
        redisTemplate.opsForHash().put(GlobalConstant.LOGIN_TOKEN, loginAuthDto.getUserId(), loginAuthDto);
        redisTemplate.expire(GlobalConstant.LOGIN_TOKEN, token_ttl, TimeUnit.DAYS);
        return uacLoginTokenVo;
    }

    @Override
    public UacLoginTokenVo refreshToken(Long userId) {
        // UacUser uacUser = userService.getById(userId);
        UacUser uacUser = new UacUser();
        return generateToken(uacUser);
    }

    @Override
    public void deleteToken(Long userId) {
        redisTemplate.opsForHash().delete(GlobalConstant.LOGIN_TOKEN, userId);
    }
}
