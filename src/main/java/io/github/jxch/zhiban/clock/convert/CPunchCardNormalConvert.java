package io.github.jxch.zhiban.clock.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import io.github.jxch.zhiban.clock.entity.User;
import org.mapstruct.Mapper;

import java.util.Calendar;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface CPunchCardNormalConvert {

    default CPunchCardNormal User2CPunchCardNormal(User user) {
        Date now = new Date();
        CPunchCardNormal cpn = new CPunchCardNormal();
        BeanUtil.copyProperties(user, cpn, CopyOptions.create().setIgnoreNullValue(true));

        return cpn.setPunchCardTime(now)
                .setCreateTime(now)
                .setUpdateTime(now)
                .setPunchCardDay(DateUtil.format(now, "yyyy-MM-dd"))
                .setDayOfWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
                .setStatus(1)
                .setMemberId(user.getUserId());
    }

    default CPunchCardNormal User2ClockInCPunchCardNormal(User user) {
        return User2CPunchCardNormal(user).setOnOffDuty(0);
    }

    default CPunchCardNormal User2ClockOutCPunchCardNormal(User user) {
        return User2CPunchCardNormal(user).setOnOffDuty(1);
    }

}
