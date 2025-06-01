package com.sniper.aiservice.service;

import com.sniper.aiservice.common.Result;
import com.sniper.aiservice.common.ResultCode;
import com.sniper.aiservice.common.ResultWrap;
import com.sniper.aiservice.dto.RequestDto;
import com.sniper.aiservice.utils.QwenUtils;
import com.sniper.aiservice.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * sniper
 * 2025/5/20 下午5:03
 */
@Slf4j
@Service
public class AiService {

    @Resource
    private QwenUtils qwenUtils;

    public Result<Object> aiRequest(RequestDto requestDto) throws Exception{
        if (StringUtils.isEmpty(requestDto.getQuestion())) {
            return ResultWrap.getFail(ResultCode.ILLEGAL_PARAMETER);
        }

        MutablePair<String, String> mutablePair = qwenUtils.requestMyQwen(requestDto.getQuestion(), requestDto.getSessionId());
//        String response = qwenUtils.request(requestDto.getQuestion());
        log.info("\n sessionId:{} \n question:{} \n answer:{}", mutablePair.right, requestDto.getQuestion(), mutablePair.left);

        return ResultWrap.getSuccess(ResponseVo.builder()
                .answer(mutablePair.left)
                .sessionId(mutablePair.right)
                .build());
    }
}
