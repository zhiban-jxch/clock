package io.github.jxch.zhiban.clock.service;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;

import java.util.List;

public interface CPunchCardNormalService {

    void clockIn(String userName);

    void clockOut(String userName);

    void clockOutOverride(String userName);

    List<CPunchCardNormal> findClock(String userName);

    void undoClock(String userName);

    boolean isClockIn(String userName);

    boolean isClockOut(String userName);

}
