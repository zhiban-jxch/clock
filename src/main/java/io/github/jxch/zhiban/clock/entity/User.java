package io.github.jxch.zhiban.clock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private String userName;
    private Long userId;
    private Long compId;
    private String equipment;
    private String location;
    private String punchCardMethod;
    private String remark;
    private List<String> remarks;
    private Long createId;
    private Long ruleId;
    private RemarkStrategy remarkStrategy;
}
