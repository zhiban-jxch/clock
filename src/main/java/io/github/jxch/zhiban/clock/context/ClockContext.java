package io.github.jxch.zhiban.clock.context;

import org.springframework.lang.NonNull;

import java.util.Date;

public class ClockContext {
    private final static ThreadLocal<Date> CLOCK_DATE = new ThreadLocal<>();

    public static void setClockDate(@NonNull Date date) {
        CLOCK_DATE.set(date);
    }

    public static Date getClockDate() {
        return CLOCK_DATE.get();
    }

    public static void removeClockDate() {
        CLOCK_DATE.remove();
    }

}
