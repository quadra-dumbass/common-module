package com.quadra.common.cookie;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CookieAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CookieUtils.class)
    public CookieUtils cookieUtils() {
        return new CookieUtils();
    }
}
