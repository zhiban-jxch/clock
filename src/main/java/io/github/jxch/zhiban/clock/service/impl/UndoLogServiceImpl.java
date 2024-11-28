package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.config.ClockConfig;
import io.github.jxch.zhiban.clock.service.UndoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UndoLogServiceImpl implements UndoLogService {
    private final ClockConfig clockConfig;

}
