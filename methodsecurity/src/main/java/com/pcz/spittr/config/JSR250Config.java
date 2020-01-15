package com.pcz.spittr.config;

import com.pcz.spittr.service.JSR250SpittleService;
import com.pcz.spittr.service.SpittleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author picongzhi
 */
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class JSR250Config extends GlobalMethodSecurityConfiguration {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    @Bean
    public SpittleService spittleService() {
        return new JSR250SpittleService();
    }
}
