package com.ts.server.mask;

import com.ts.server.mask.configure.MaskProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 程序入口程序
 *
 * @author TS Group
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableConfigurationProperties({MaskProperties.class})
public class MaskApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaskApplication.class);

    public static void main(String[] args){
        ApplicationContext context = new SpringApplicationBuilder(MaskApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

        for(String name: context.getBeanDefinitionNames()){
            LOGGER.trace("Instance bean name={}", name);
        }
    }
}
