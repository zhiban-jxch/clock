package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.convert.CPunchCardNormalConvert;
import io.github.jxch.zhiban.clock.dao.CPunchCardStateRepository;
import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.CPunchCardState;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.CPunchCardStateService;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CPunchCardStateServiceImpl implements CPunchCardStateService {
    private final CPunchCardStateRepository cPunchCardStateRepository;
    private final CPunchCardNormalConvert cPunchCardNormalConvert;
    private final UserConfigService userConfigService;

    @Override
    public void updateClockOutStatus(Long memberId, Long compId, String day) {
        List<CPunchCardState> po = cPunchCardStateRepository.findByMemberIdAndCompIdAndPunchCardDay(memberId, compId, day)
                .stream().peek(item -> item.setLackCard(0).setPunchCardStatus("1")).toList();
        cPunchCardStateRepository.saveAllAndFlush(po);
    }

    @Override
    public boolean needInsert(String username) {
        User user = userConfigService.getUserByName(username);
        CPunchCardNormal dto = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        return cPunchCardStateRepository.findByMemberIdAndCompIdAndPunchCardDay(dto.getMemberId(), dto.getCompId(), dto.getPunchCardDay()).isEmpty();
    }

    @Override
    public void insert(String username) {
        User user = userConfigService.getUserByName(username);
        CPunchCardNormal dto = cPunchCardNormalConvert.User2ClockOutCPunchCardNormal(user);
        CPunchCardState po = cPunchCardNormalConvert.cPunchCardNormal2CPunchCardState(dto);
        cPunchCardStateRepository.saveAndFlush(po);
    }

}
