package com.zjut.factory.server.biz.beanhandler.invokemsg;

import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 视频类型消息处理器
 */
@Component(CommConstants.VIDEO)
public class VideoMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        return null;
    }
}
