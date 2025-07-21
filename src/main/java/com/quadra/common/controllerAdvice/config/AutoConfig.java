package com.quadra.common.controllerAdvice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadra.common.controllerAdvice.handler.GlobalControllerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AutoConfig {

    @Bean
    // 이미 같은 Advice가 없다면 등록
    @ConditionalOnMissingBean(GlobalControllerAdvice.class)
    public GlobalControllerAdvice globalControllerAdvice(ObjectMapper objectMapper) {
        return new GlobalControllerAdvice(objectMapper);
    }

}
