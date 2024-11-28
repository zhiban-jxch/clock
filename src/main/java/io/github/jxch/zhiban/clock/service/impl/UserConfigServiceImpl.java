package io.github.jxch.zhiban.clock.service.impl;

import cn.hutool.json.JSONUtil;
import io.github.jxch.zhiban.clock.config.ClockConfig;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.entity.UserConfig;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {
    private final ClockConfig clockConfig;

    @Override
    public UserConfig userConfig() {
        return JSONUtil.readJSONObject(clockConfig.userConfigFile(), StandardCharsets.UTF_8).toBean(UserConfig.class);
    }

    @Override
    public List<User> allUsers() {
        return userConfig().getUsers();
    }

    @Override
    public User getUserByName(String username) {
        return allUsers().stream().filter(user -> Objects.equals(username, user.getUserName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("没有这个用户：" + username));
    }

    @Override
    @SneakyThrows
    public void insertUser(User user) {
        if (allUsers().stream().anyMatch(u -> Objects.equals(u.getUserName(), user.getUserName()))) {
            throw new IllegalArgumentException("用户名已存在：" + user.getUserName());
        }

        UserConfig userConfig = userConfig();
        userConfig.getUsers().add(user);
        String json = JSONUtil.toJsonPrettyStr(userConfig);
        FileUtils.write(clockConfig.userConfigFile(), StandardCharsets.UTF_8.name(), json);
    }

}
