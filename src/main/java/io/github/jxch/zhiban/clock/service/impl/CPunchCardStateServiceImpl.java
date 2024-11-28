package io.github.jxch.zhiban.clock.service.impl;

import io.github.jxch.zhiban.clock.dao.CPunchCardStateRepository;
import io.github.jxch.zhiban.clock.entity.CPunchCardState;
import io.github.jxch.zhiban.clock.service.CPunchCardStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CPunchCardStateServiceImpl implements CPunchCardStateService {
    private final CPunchCardStateRepository cPunchCardStateRepository;
    @Override
    public void updateClockOutStatus(Long memberId, Long compId, String day) {
        CPunchCardState po = cPunchCardStateRepository.findByMemberIdAndCompIdAndPunchCardDay(memberId, compId, day);
        cPunchCardStateRepository.saveAndFlush(po.setLackCard(0).setPunchCardStatus("1"));
    }
}
