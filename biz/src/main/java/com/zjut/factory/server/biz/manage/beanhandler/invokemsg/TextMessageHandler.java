package com.zjut.factory.server.biz.manage.beanhandler.invokemsg;

import com.zjut.factory.server.biz.manage.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文本类型消息处理器
 */
@Component(CommConstants.TEXT)
public class TextMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        TextMessage tm = new TextMessage(data, "点击<a href=\"www.baidu.com\">这里</a>登录");
        tm.setToUserName(data.get("FromUserName"));
        tm.setFromUserName(data.get("ToUserName"));
        tm.setCreateTime(System.currentTimeMillis()/1000+"");
        return tm;
    }
}
