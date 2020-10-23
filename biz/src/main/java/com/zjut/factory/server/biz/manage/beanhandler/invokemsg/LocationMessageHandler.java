package com.zjut.factory.server.biz.manage.beanhandler.invokemsg;

import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 地理位置类型消息处理器
 */
@Component(CommConstants.LOCATION)
public class LocationMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        return null;
    }
}
