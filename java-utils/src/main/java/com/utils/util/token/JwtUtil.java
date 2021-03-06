package com.utils.util.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class JwtUtil {

    @Data
    public class JWTInfo {
        private String token;
        private Long expireIn;
    }

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     */
    public JWTInfo createJWT(Date issuedAt, Date expiration, TokenObject tokenObject) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", tokenObject.getUserId());
        claims.put("timestamp", issuedAt.getTime() / 1000L);
        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = tokenObject.getSecretKey();
        // 生成签发人
        Long subject = tokenObject.getUserId();

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为单次约会token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                // iat: jwt的签发时间
                .setIssuedAt(issuedAt)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(String.valueOf(subject))
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        // 设置过期时间
        builder.setExpiration(expiration);
        final String token = builder.compact();
        JWTInfo jwtInfo = new JWTInfo();
        jwtInfo.setToken(token);
        jwtInfo.setExpireIn(expiration.getTime() / 1000L);
        return jwtInfo;
    }

    /**
     * Token的解密
     */
    public Claims parseJWT(String token, TokenObject tokenObject) {
        // 签名秘钥，和生成的签名的秘钥一模一样
        String key = tokenObject.getSecretKey();
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public Boolean isVerify(String token, TokenObject tokenObject) {
        // 签名秘钥，和生成的签名的秘钥一模一样
        String key = tokenObject.getSecretKey();
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (claims.get("secret_key").equals(tokenObject.getSecretKey())) {
            return true;
        }

        return false;
    }
}
