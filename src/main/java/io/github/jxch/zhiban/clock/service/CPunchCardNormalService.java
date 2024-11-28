package io.github.jxch.zhiban.clock.service;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;

import java.util.List;

public interface CPunchCardNormalService {

    void clockIn(String userName);

    void clockOut(String userName);

    List<CPunchCardNormal> findClock(String userName);

    void undoClock(String userName);

}
