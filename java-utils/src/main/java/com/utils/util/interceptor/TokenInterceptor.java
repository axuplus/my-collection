package com.utils.util.interceptor;

import com.utils.annotation.Permission;
import com.utils.util.PublicUtil;
import com.utils.util.ThreadLocalMap;
import com.utils.util.constant.GlobalConstant;
import com.utils.util.dto.LoginAuthDto;
import com.utils.util.dto.UserLocationDto;
import com.utils.util.exception.BizException;
import com.utils.util.token.JwtUtil;
import com.utils.util.token.TokenObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Value(value = "${token.secretKey}")
    private String token_secret_key;

    @Value(value = "${token.ttl}")
    private Integer token_ttl;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * After completion.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param ex       the ex
     * @throws Exception the exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) throws Exception {
        if (ex != null) {
            log.error("<== afterCompletion - 解析token失败. ex={}", ex.getMessage(), ex);
            this.handleException(response);
        }
    }

    /**
     * Post handle.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param mv       the mv
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) {
    }

    /**
     * Pre handle boolean.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @return the boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        log.info("<== preHandle - 拦截器.  url={}", uri);

        // 获取请求头token
        String token = request.getHeader("token");
        log.info("token= {}", token);

        if (PublicUtil.isEmpty(token)) {
            throw new BizException(403, "check token fail");
        }

        TokenObject tokenObject = new TokenObject();
        tokenObject.setSecretKey(token_secret_key);
        Long userId;
        try {
            // 解密
            final Claims claims = new JwtUtil().parseJWT(token, tokenObject);
            userId = Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(403, "check token fail");
        }

        log.info("userId {}", userId);
        if (PublicUtil.isEmpty(userId)) {
            throw new BizException(403, "check token fail");
        }
        LoginAuthDto loginUser = (LoginAuthDto) redisTemplate.opsForHash().get(GlobalConstant.LOGIN_TOKEN, userId);
        if (PublicUtil.isEmpty(loginUser)) {
            log.error("获取用户信息失败, 不允许操作");
            throw new BizException(403, "check token fail");
        }
        // 比较
        // 登录往redis里面存的时候进行加密  String token = Base64.encodeBase64URLSafeString(jwtInfo.getToken().getBytes());
        String loginToken = new String(Base64.decodeBase64(loginUser.getToken()));

        if (!token.equals(loginToken)) {
            throw new BizException(403, "check token fail");
        }
        UserLocationDto userLocationDto = (UserLocationDto) redisTemplate.opsForHash().get(GlobalConstant.USER_LOCATION, userId);
        if (PublicUtil.isNotEmpty(userLocationDto)) {
            log.info("userId={},lng={},lat={}", userLocationDto.getUserId(), userLocationDto.getLng(), userLocationDto.getLat());
            loginUser.setLng(userLocationDto.getLng());
            loginUser.setLat(userLocationDto.getLat());
            loginUser.setProvince(userLocationDto.getProvince());
            loginUser.setCity(userLocationDto.getCity());
        }
        log.info("<== preHandle - 权限拦截器.  loginUser={}", loginUser);
        Permission allowedDo = isAllowedDo(handler);
        log.warn("allowedDo {}", allowedDo);
        // 权限逻辑
        ThreadLocalMap.put(GlobalConstant.Sys.TOKEN_AUTH_DTO, loginUser);
        return true;
    }

    /**
     * 验证注解
     */
    private Permission isAllowedDo(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        return AnnotationUtils.findAnnotation(method, Permission.class);
    }

    private void handleException(HttpServletResponse res) throws IOException {
        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("{\"code\":100009 ,\"message\" :\"解析token失败\"}");
        res.flushBuffer();
    }

}
