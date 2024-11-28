package io.github.jxch.zhiban.clock.service;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;

import java.util.List;

public interface CPunchCardNormalService {

    boolean clockIn(String userName);

    boolean clockOut(String userName);

    List<CPunchCardNormal> findClock(String userName);

    boolean undoClock(String userName);

}
