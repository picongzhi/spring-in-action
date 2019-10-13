package com.pcz.knights.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

/**
 * @author picongzhi
 */
@Configuration
@ComponentScan(basePackages = "com.pcz.soundsystem", excludeFilters = {@Filter(Configuration.class)})
public class SoundSystemConfig {
}
