package com.pcz.knights.config;

import com.pcz.knights.BraveKnight;
import com.pcz.knights.Knight;
import com.pcz.knights.Quest;
import com.pcz.knights.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author picongzhi
 */
@Configuration
public class KnightConfig {
    @Bean
    public Knight knight() {
        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }
}
