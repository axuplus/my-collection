package com.utils.util.interceptor;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.utils.util.PublicUtil;
import com.utils.util.RequestUtil;
import com.utils.util.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "token")
public class TokenFilter extends ZuulFilter {

    private Set<String> whites;

    @Override
    public String filterType() {
        // 前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("AuthHeaderFilter - 开始鉴权...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        doSomething(requestContext);
        return null;
    }

    private void doSomething(RequestContext requestContext) throws ZuulException {
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();
        log.info("0000000000>"+request.getParameterMap());
        String token = RequestUtil.getToken(request);
        if(PublicUtil.isNotEmpty(token)){
            requestContext.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, token);
        }
        requestContext.setRequest(request);
        log.info("The request url {}", requestURI);
        if (PublicUtil.isNotEmpty(whites)) {
            if (whites.contains(requestURI)) {
                log.info("不需要校验token, {}", requestURI);
                return;
            }
        }
        log.info("token={}", token);
        if (PublicUtil.isEmpty(token)) {
            throw new ZuulException("刷新页面重试", ErrorCodeEnum.GL99990101.code(), ErrorCodeEnum.GL99990101.msg());
        }
        // 让TokenInterceptor去解密
        requestContext.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, token);
    }
}
