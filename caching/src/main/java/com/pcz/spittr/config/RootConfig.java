package com.pcz.spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author picongzhi
 */
@Configuration
@ComponentScan("com.pcz.spittr.db")
@Import({DataConfig.class, CachingConfig.class})
public class RootConfig {
}
