package io.github.jxch.zhiban.clock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Comment("上下班打卡表")
@Table(name = "c_punch_card_normal")
public class CPunchCardNormal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("打卡主键id")
    private Long punchCardId;

    @Comment("公司id")
    private Long compId;

    @Comment("员工id")
    private Long memberId;

    @Comment("状态 1 有效  2 无效")
    private Integer status;

    @Comment("打卡时间")
    private Date punchCardTime;

    @Comment("设备")
    private String equipment;

    @Comment("打卡地点")
    private String location;

    @Comment("打卡的日期哪天")
    private String punchCardDay;

    @Comment("周几（1-7）")
    private Integer dayOfWeek;

    @Comment("当前打卡规则的id")
    private Long ruleId;

    @Comment("上班打卡0 或 下班打卡 1")
    private Integer onOffDuty;

    @Comment("打卡方式")
    private String punchCardMethod;

    @Comment("打卡的时间点")
    private String workTime;

    @Comment("备注（文字或图片url地址）")
    private String remark;

    @Comment("创建人")
    private Long createId;

    @Comment("创建时间")
    private Date createTime;

    @Comment("更新人id")
    private Long updateId;

    @Comment("更新时间")
    private Date updateTime;
}

