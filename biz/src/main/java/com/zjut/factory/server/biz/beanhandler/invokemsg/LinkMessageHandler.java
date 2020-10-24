package com.zjut.factory.server.biz.beanhandler.invokemsg;

import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 链接类型消息处理器
 */
@Component(CommConstants.LINK)
public class LinkMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        return null;
    }
}
