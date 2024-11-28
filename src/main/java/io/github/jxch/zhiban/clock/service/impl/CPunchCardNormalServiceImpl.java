package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import io.github.jxch.zhiban.clock.service.UndoLogService;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CPunchCardNormalServiceImpl implements CPunchCardNormalService {
    private final UserConfigService userConfigService;
    private final UndoLogService undoLogService;

    @Override
    public boolean clockIn(String userName) {

        return false;
    }

    @Override
    public boolean clockOut(String userName) {
        return false;
    }

    @Override
    public List<CPunchCardNormal> findClock(String userName) {
        return List.of();
    }

    @Override
    public boolean undoClock(String userName) {
        return false;
    }

}
