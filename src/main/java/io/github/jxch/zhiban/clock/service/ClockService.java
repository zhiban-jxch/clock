package io.github.jxch.zhiban.clock.service;

public interface ClockService {

    void clockIn(String userName);

    void clockOut(String userName);

    boolean isClockIn(String userName);

    boolean isClockOut(String userName);
}
