package com.utils.util.feign;

/**
 * import feign.Feign;
 * import feign.Logger;
 * import feign.RequestInterceptor;
 * import feign.codec.ErrorDecoder;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.context.annotation.Scope;
 *
 * @Configuration public class FeignAutoConfiguration {
 * @Bean public RequestInterceptor oauth2FeignRequestInterceptor() {
 * return new FeignTokenInterceptor();
 * }
 * @Bean Logger.Level feignLoggerLevel() {
 * return Logger.Level.FULL;
 * }
 * @Bean
 * @Scope("prototype") public Feign.Builder feignBuilder() {
 * return Feign.builder();
 * }
 * @Bean public ErrorDecoder errorDecoder() {
 * return new FeignErrorInterceptor();
 * }
 * }
 **/
