package com.zjut.factory.server.biz.beanhandler.invokemsg;

import com.zjut.factory.server.biz.entity.msg.BaseMessage;
import com.zjut.factory.server.biz.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 事件类型消息处理器
 */
@Component(CommConstants.EVENT)
public class EventMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        String event = data.get(CommConstants.E_EVENT);
        if (StringUtils.isBlank(event)) {
            return this.getResp(data, "操作公众号失败");
        }

        BaseMessage result = null;
        switch (event) {
            case "subscribe" :
                result = this.getResp(data, "关注成功");
                break;
            case "unsubscribe":
                result = this.getResp(data, "取关成功");
                break;
            default:
                result = this.getResp(data, "未知操作类型");
        }

        return result;
    }

    /**
     * 封装回复的消息结果
     * @param data
     * @param content
     * @return
     */
    private BaseMessage getResp(Map<String, String> data, String content) {
        TextMessage tm = new TextMessage(data, content);
        tm.setToUserName(data.get(CommConstants.FROM_USER_NAME));
        tm.setFromUserName(data.get(CommConstants.TO_USER_NAME));
        tm.setCreateTime(System.currentTimeMillis()/1000+"");
        return tm;
    }
}
