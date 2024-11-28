package io.github.jxch.zhiban.clock.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Comment("打卡状态表")
@Table(name = "c_punch_card_state")
public class CPunchCardState  {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Comment("打卡状态表")
    private Long punchCardStateId;

@Comment("打卡是哪天")
    private String punchCardDay;
/**
     * 打卡人员id
     */
    private Long memberId;
/**
     * 打卡人员公司id
     */
    private Long compId;
/**
     * 打卡的状态(1 正常 2 迟到 3 早退 4 缺卡)
     */
    private String punchCardStatus;
/**
     * 应该打卡的次数
     */
    private Integer shouldPunchCard;
/**
     * 早退次数
     */
    private Integer leaveEarly;
/**
     * 迟到次数
     */
    private Integer beLate;
/**
     * 缺卡次数
     */
    private Integer lackCard;
/**
     * 状态 1 有效  2 无效 
     */
    private Integer status;
/**
     * 打卡规则id
     */
    private Long ruleId;
/**
     * 创建人id
     */
    private Long createId;
/**
     * 创建时间
     */
    private Date createTime;
/**
     * 更新人id
     */
    private Long updateId;
/**
     * 更新时间
     */
    private Date updateTime;


}

