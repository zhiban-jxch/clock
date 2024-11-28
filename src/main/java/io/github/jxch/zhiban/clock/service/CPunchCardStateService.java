package io.github.jxch.zhiban.clock.service;

public interface CPunchCardStateService {

    void updateClockOutStatus(Long memberId, Long compId, String day);

}
