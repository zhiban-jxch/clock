package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import io.github.jxch.zhiban.clock.service.ClockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClockServiceImpl implements ClockService {
    private final CPunchCardNormalService cPunchCardNormalService;

    @Override
    public void clockIn(String userName) {
        if (!isClockIn(userName)) {
            cPunchCardNormalService.clockIn(userName);
        }
    }

    @Override
    public void clockOut(String userName) {
        if (!isClockOut(userName)) {
            cPunchCardNormalService.clockOut(userName);
        }
    }

    @Override
    public boolean isClockIn(String userName) {
        return cPunchCardNormalService.isClockIn(userName);
    }

    @Override
    public boolean isClockOut(String userName) {
        return cPunchCardNormalService.isClockOut(userName);
    }

}
