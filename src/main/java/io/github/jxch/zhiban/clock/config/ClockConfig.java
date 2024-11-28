package io.github.jxch.zhiban.clock.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Data
@Configuration
public class ClockConfig {
    @Value("${data-dir}")
    private String dataDir;

    public File userConfigFile() {
        return new File(dataDir, "userConfig.json");
    }

    public File undoLogFile() {
        return new File(dataDir, "undoLog.json");
    }

}
