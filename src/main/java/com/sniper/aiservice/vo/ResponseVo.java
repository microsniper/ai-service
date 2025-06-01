package com.sniper.aiservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sniper
 * 2025/5/30 下午6:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo implements Serializable {

    private String answer;

    private String sessionId;
}
