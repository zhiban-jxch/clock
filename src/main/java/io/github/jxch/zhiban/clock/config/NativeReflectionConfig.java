package io.github.jxch.zhiban.clock.config;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.CPunchCardState;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.entity.UserConfig;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({
        CPunchCardNormal.class, CPunchCardState.class, UserConfig.class, User.class
})
public class NativeReflectionConfig {
}
