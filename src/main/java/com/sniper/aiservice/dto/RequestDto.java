package com.sniper.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sniper
 * 2025/5/20 下午5:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto implements Serializable {

    private String question;

    private String sessionId;
}
