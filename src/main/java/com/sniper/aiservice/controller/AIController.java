package com.sniper.aiservice.controller;

import com.sniper.aiservice.common.Result;
import com.sniper.aiservice.common.ResultCode;
import com.sniper.aiservice.common.ResultWrap;
import com.sniper.aiservice.common.exception.BaseException;
import com.sniper.aiservice.dto.RequestDto;
import com.sniper.aiservice.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * sniper
 * 2025/5/20 下午4:32
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class AIController {

    @Resource
    private AiService aiService;

    @PostMapping("/chat")
    public Result<Object> aiRequest(@RequestBody RequestDto requestDto) {
        try {
            return aiService.aiRequest(requestDto);
        }catch (Exception e) {
            log.error("chat exception:", e);
            return ResultWrap.getFail(ResultCode.FAILED);
        }
    }
}
