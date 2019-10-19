package com.pcz.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvironmentConfigWithDefaults {
    @Autowired
    private Environment environment;

    @Bean
    public BlankDisc blankDisc() {
        return new BlankDisc(environment.getProperty(
                "disc.title", "Rattle and Hum"),
                environment.getProperty("disc.artist", "U2"));
    }
}
