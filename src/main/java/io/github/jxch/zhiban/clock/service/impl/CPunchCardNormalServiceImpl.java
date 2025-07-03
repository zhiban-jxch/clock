package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.convert.CPunchCardNormalConvert;
import io.github.jxch.zhiban.clock.dao.CPunchCardNormalRepository;
import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import io.github.jxch.zhiban.clock.service.CPunchCardStateService;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class CPunchCardNormalServiceImpl implements CPunchCardNormalService {
    private final CPunchCardNormalRepository cPunchCardNormalRepository;
    private final CPunchCardNormalConvert cPunchCardNormalConvert;
    private final UserConfigService userConfigService;
    private final CPunchCardStateService cPunchCardStateService;

    @Override
    public void clockIn(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockInCPunchCardNormal(user);
        cPunchCardNormalRepository.save(cPunchCardNormal);
    }

    @Override
    public void clockOut(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        CPunchCardNormal saved = cPunchCardNormalRepository.save(cPunchCardNormal);
        cPunchCardStateService.updateClockOutStatus(saved.getMemberId(), saved.getCompId(), saved.getPunchCardDay());
    }

    @Override
    public List<CPunchCardNormal> findClockInList(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockInCPunchCardNormal(user);
        return cPunchCardNormalRepository.findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(
                        cPunchCardNormal.getMemberId(), cPunchCardNormal.getCompId(), cPunchCardNormal.getPunchCardDay(), cPunchCardNormal.getOnOffDuty())
                .stream().toList();
    }

    @Override
    public List<CPunchCardNormal> findClockOutList(String userName) {
        User user = userConfigService.getUserByName(userName);
        CPunchCardNormal cPunchCardNormal = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        return cPunchCardNormalRepository.findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(
                        cPunchCardNormal.getMemberId(), cPunchCardNormal.getCompId(), cPunchCardNormal.getPunchCardDay(), cPunchCardNormal.getOnOffDuty())
                .stream().toList();
    }

    @Override
    public void undoClock(String userName) {
    }

    @Override
    public boolean isClockIn(String userName) {
        return !findClockInList(userName).isEmpty();
    }

    @Override
    public boolean isClockOut(String userName) {
        return !findClockOutList(userName).isEmpty();
    }

    @Override
    public String remark(User user, Integer onOffDuty) {
        switch (user.getRemarkStrategy()) {
            case DB -> {
                List<String> remarks = new ArrayList<>(Objects.isNull(user.getTimeAfter()) ?
                        cPunchCardNormalRepository.findByMemberIdAndCompIdAndOnOffDuty(user.getUserId(), user.getCompId(), onOffDuty).stream().map(CPunchCardNormal::getRemark).toList() :
                        cPunchCardNormalRepository.findByMemberIdAndCompIdAndOnOffDutyAndCreateTimeAfter(user.getUserId(), user.getCompId(), onOffDuty, user.getTimeAfter()).stream().map(CPunchCardNormal::getRemark).toList()
                );
                Collections.shuffle(remarks);
                return remarks.stream().findAny().orElse(user.getRemark());
            }
            case JSON_ARR -> {
                List<String> remarks = user.getRemarks();
                Collections.shuffle(remarks);
                return remarks.stream().findAny().orElse(user.getRemark());
            }
            default -> {
                return user.getRemark();
            }
        }
    }


}
