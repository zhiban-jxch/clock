package io.github.jxch.zhiban.clock.dao;

import io.github.jxch.zhiban.clock.entity.CPunchCardState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPunchCardStateRepository extends JpaRepository<CPunchCardState, Long> {

    List<CPunchCardState> findByMemberIdAndCompIdAndPunchCardDay(Long memberId, Long compId, String punchCardDay);

}
