package com.utils.util.feign;
/**
 * import com.utils.util.constant.GlobalConstant;
 * import feign.RequestInterceptor;
 * import feign.RequestTemplate;
 * import lombok.extern.slf4j.Slf4j;
 * import org.springframework.web.context.request.RequestContextHolder;
 * import org.springframework.web.context.request.ServletRequestAttributes;
 * <p>
 * import javax.servlet.http.HttpServletRequest;
 *
 * @Slf4j public class FeignTokenInterceptor implements RequestInterceptor {
 * @Override public void apply(RequestTemplate requestTemplate) {
 * ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
 * HttpServletRequest request = attributes.getRequest();
 * String token = request.getHeader(GlobalConstant.Sys.X_LABEL);
 * requestTemplate.header(GlobalConstant.Sys.X_LABEL, token);
 * }
 * }
 **/
