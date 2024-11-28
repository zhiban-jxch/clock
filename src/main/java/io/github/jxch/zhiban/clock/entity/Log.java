package io.github.jxch.zhiban.clock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Log {
    private String clockId;
    private String userName;
    private String userId;
    private String clockType;
    private String clockTime;
}
