package io.github.jxch.zhiban.clock.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.CPunchCardState;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.CPunchCardNormalService;
import org.mapstruct.Mapper;

import java.util.Calendar;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface CPunchCardNormalConvert {

    default int dayOfWeek() {
        return (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
    }

    default CPunchCardNormal User2CPunchCardNormal(User user) {
        Date now = new Date();
        CPunchCardNormal cpn = new CPunchCardNormal();
        BeanUtil.copyProperties(user, cpn, CopyOptions.create().setIgnoreNullValue(true));

        return cpn.setPunchCardTime(now)
                .setCreateTime(now)
                .setUpdateTime(now)
                .setPunchCardDay(DateUtil.format(now, "yyyy-MM-dd"))
                .setDayOfWeek(dayOfWeek())
                .setStatus(1)
                .setMemberId(user.getUserId());
    }

    default CPunchCardNormal User2ClockInCPunchCardNormal(User user) {
        CPunchCardNormalService service = SpringUtil.getBean(CPunchCardNormalService.class);
        return User2CPunchCardNormal(user).setOnOffDuty(0).setWorkTime("09:00").setRemark(service.remark(user, 0));
    }

    default CPunchCardNormal User2ClockOutCPunchCardNormal(User user) {
        CPunchCardNormalService service = SpringUtil.getBean(CPunchCardNormalService.class);
        return User2CPunchCardNormal(user).setOnOffDuty(1).setWorkTime("18:00").setRemark(service.remark(user, 1));
    }

    default CPunchCardState cPunchCardNormal2CPunchCardState(CPunchCardNormal cPunchCardNormal) {
        Date now = new Date();
        CPunchCardState cPunchCardState = new CPunchCardState();
        BeanUtil.copyProperties(cPunchCardNormal, cPunchCardState);
        return cPunchCardState
                .setPunchCardStatus("1")
                .setShouldPunchCard(dayOfWeek() > 5 ? 0 : 2)
                .setLeaveEarly(0)
                .setBeLate(0)
                .setLackCard(0)
                .setStatus(1)
                .setCreateId(null)
                .setUpdateId(null)
                .setCreateTime(now)
                .setUpdateTime(now);
    }

}
