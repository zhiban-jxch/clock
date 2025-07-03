package io.github.jxch.zhiban.clock.service;

import java.util.Date;
import java.util.List;

public interface ClockService {

    void clockIn(String userName);

    void clockOut(String userName);

    boolean isClockIn(String userName);

    boolean isClockOut(String userName);

    List<Date> getClockInDate(String userName);

    List<Date> getClockOutDate(String userName);
}
