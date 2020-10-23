package com.zjut.factory.server.biz.manage.beanhandler.invokemsg;

import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 图片类型消息处理器
 */
@Component(CommConstants.IMAGE)
public class ImageMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        return null;
    }
}
