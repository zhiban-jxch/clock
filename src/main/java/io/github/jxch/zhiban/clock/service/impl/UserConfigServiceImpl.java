package io.github.jxch.zhiban.clock.service.impl;

import cn.hutool.json.JSONUtil;
import io.github.jxch.zhiban.clock.config.ClockConfig;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.entity.UserConfig;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {
    private final ClockConfig clockConfig;

    @Override
    public List<User> allUsers() {
        return JSONUtil.readJSONObject(clockConfig.userConfigFile(), StandardCharsets.UTF_8)
                .toBean(UserConfig.class).getUsers();
    }

}
