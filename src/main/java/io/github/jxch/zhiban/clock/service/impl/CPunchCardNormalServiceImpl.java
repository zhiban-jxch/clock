package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.convert.CPunchCardNormalConvert;
import io.github.jxch.zhiban.clock.dao.CPunchCardNormalRepository;
import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import io.github.jxch.zhiban.clock.service.CPunchCardStateService;
import io.github.jxch.zhiban.clock.service.UndoLogService;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CPunchCardNormalServiceImpl implements CPunchCardNormalService {
    private final CPunchCardNormalRepository cPunchCardNormalRepository;
    private final CPunchCardNormalConvert cPunchCardNormalConvert;
    private final UserConfigService userConfigService;
    private final UndoLogService undoLogService;
    private final CPunchCardStateService cPunchCardStateService;

    @Override
    public void clockIn(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockInCPunchCardNormal(user);
        CPunchCardNormal saved = cPunchCardNormalRepository.save(cPunchCardNormal);

        // todo 记录 undo log 日志

    }

    @Override
    public void clockOut(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        CPunchCardNormal saved = cPunchCardNormalRepository.save(cPunchCardNormal);
        cPunchCardStateService.updateClockOutStatus(saved.getMemberId(), saved.getCompId(), saved.getPunchCardDay());

        // todo 记录 undo log 日志
    }

    @Override
    public void clockOutOverride(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal tmp = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        CPunchCardNormal record = cPunchCardNormalRepository.findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(tmp.getMemberId(), tmp.getCompId(), tmp.getPunchCardDay(), tmp.getOnOffDuty());
        record = cPunchCardNormalConvert.updateTime(record);
        cPunchCardNormalRepository.save(record);
    }

    @Override
    public List<CPunchCardNormal> findClock(String userName) {
        return List.of();
    }

    @Override
    public void undoClock(String userName) {
    }

    @Override
    public boolean isClockIn(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockInCPunchCardNormal(user);
        CPunchCardNormal po = cPunchCardNormalRepository.findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(
                cPunchCardNormal.getMemberId(), cPunchCardNormal.getCompId(), cPunchCardNormal.getPunchCardDay(), cPunchCardNormal.getOnOffDuty());
        return Objects.nonNull(po);
    }

    @Override
    public boolean isClockOut(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        CPunchCardNormal po = cPunchCardNormalRepository.findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(
                cPunchCardNormal.getMemberId(), cPunchCardNormal.getCompId(), cPunchCardNormal.getPunchCardDay(), cPunchCardNormal.getOnOffDuty());
        return Objects.nonNull(po);
    }

}
