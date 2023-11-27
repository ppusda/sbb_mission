package com.sbb.sbb_mission;

import com.sbb.sbb_mission.global.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class})
public class SbbMissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbbMissionApplication.class, args);
    }

}
