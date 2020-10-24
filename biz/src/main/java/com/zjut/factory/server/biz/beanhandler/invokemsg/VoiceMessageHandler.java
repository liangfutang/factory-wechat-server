package com.zjut.factory.server.biz.beanhandler.invokemsg;

import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 语音类型消息处理器
 */
@Component(CommConstants.VOICE)
public class VoiceMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        return null;
    }
}
