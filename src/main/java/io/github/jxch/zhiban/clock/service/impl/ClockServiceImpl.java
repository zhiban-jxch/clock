package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import io.github.jxch.zhiban.clock.service.CPunchCardStateService;
import io.github.jxch.zhiban.clock.service.ClockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClockServiceImpl implements ClockService {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final CPunchCardNormalService cPunchCardNormalService;
    private final CPunchCardStateService cPunchCardStateService;

    @Override
    public synchronized void clockIn(String userName) {
        if (!isClockIn(userName)) {
            cPunchCardNormalService.clockIn(userName);
            if (cPunchCardStateService.needInsert(userName)) {
                cPunchCardStateService.insert(userName);
            }
        }
    }

    @Override
    public synchronized void clockOut(String userName) {
        cPunchCardNormalService.clockOut(userName);
    }

    @Override
    public boolean isClockIn(String userName) {
        return cPunchCardNormalService.isClockIn(userName);
    }

    @Override
    public boolean isClockOut(String userName) {
        return cPunchCardNormalService.isClockOut(userName);
    }

    @Override
    public List<Date> getClockInDate(String userName) {
        return cPunchCardNormalService.findClockInList(userName).stream().map(CPunchCardNormal::getPunchCardTime).sorted().toList();
    }

    @Override
    public List<Date> getClockOutDate(String userName) {
        return cPunchCardNormalService.findClockOutList(userName).stream().map(CPunchCardNormal::getPunchCardTime).sorted().toList();
    }

}
