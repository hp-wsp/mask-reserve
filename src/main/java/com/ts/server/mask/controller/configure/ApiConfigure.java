package com.ts.server.mask.controller.configure;

import com.ts.server.mask.controller.interceptor.AuthorizationInterceptor;
import com.ts.server.mask.security.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Api接口配置
 *
 * @author TS Group
 */
@Configuration
public class ApiConfigure implements WebMvcConfigurer {
    private final TokenService tokenService;

    @Autowired
    public ApiConfigure(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(tokenService))
                .addPathPatterns("/**")
                .excludePathPatterns("/client/**/*", "/man/main/*", "/error");
    }
}
