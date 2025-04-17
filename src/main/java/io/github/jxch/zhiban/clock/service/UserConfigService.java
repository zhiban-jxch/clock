package io.github.jxch.zhiban.clock.service;

import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.entity.UserConfig;

import java.util.List;

public interface UserConfigService {

    UserConfig userConfig();

    List<User> allUsers();

    User getUserByName(String username);

}
