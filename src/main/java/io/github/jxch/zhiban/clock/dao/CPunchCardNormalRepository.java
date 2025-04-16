package io.github.jxch.zhiban.clock.dao;

import io.github.jxch.zhiban.clock.entity.CPunchCardNormal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CPunchCardNormalRepository extends JpaRepository<CPunchCardNormal, Long> {

    List<CPunchCardNormal> findByMemberIdAndCompIdAndPunchCardDayAndOnOffDuty(Long memberId, Long compId, String day, Integer onOffDuty);

    List<CPunchCardNormal> findByMemberIdAndCompIdAndOnOffDuty(Long memberId, Long compId, Integer onOffDuty);

    List<CPunchCardNormal> findByMemberIdAndCompIdAndOnOffDutyAndCreateTimeAfter(Long memberId, Long compId, Integer onOffDuty, Date createTimeAfter);

}
