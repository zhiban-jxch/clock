package io.github.jxch.zhiban.clock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jxch.zhiban.clock.entity.UserConfig;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@Configuration
@EnableScheduling
public class ClockConfig {

    @Bean
    @SneakyThrows
    public UserConfig userConfig() {
        return new ObjectMapper().readValue(new ClassPathResource("userConfig.json").getInputStream(), UserConfig.class);
    }

}
