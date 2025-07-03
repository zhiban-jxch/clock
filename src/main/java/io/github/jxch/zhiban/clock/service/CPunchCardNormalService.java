package io.github.jxch.zhiban.clock.service;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.User;

import java.util.List;

public interface CPunchCardNormalService {

    void clockIn(String userName);

    void clockOut(String userName);

    List<CPunchCardNormal> findClockInList(String userName);

    List<CPunchCardNormal> findClockOutList(String userName);

    void undoClock(String userName);

    boolean isClockIn(String userName);

    boolean isClockOut(String userName);

    String remark(User user, Integer onOffDuty);

}
