package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.entity.UserConfig;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {
    private final UserConfig userConfig;

    @Override
    public UserConfig userConfig() {
        return userConfig;
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

}
