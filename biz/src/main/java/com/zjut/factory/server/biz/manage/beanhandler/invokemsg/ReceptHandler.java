package com.zjut.factory.server.biz.manage.beanhandler.invokemsg;

import java.util.Map;

public interface ReceptHandler {

    /**
     * 处理收到的公众号消息
     * @return
     */
    Object invokeHandler(Map<String, String> data);
}
