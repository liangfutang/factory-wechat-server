package com.zjut.factory.server.biz.manage;

import com.zjut.factory.server.biz.beanhandler.invokemsg.ReceptHandler;
import com.zjut.factory.server.biz.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用来处理接收到的微信公众号消息，并作出返回动作
 */
@Component
@Slf4j
public class WechatMsgManage {

    @Autowired
    private Map<String, ReceptHandler> receptHandlerMap;

    /**
     * 解析传进来的消息，并作出相应的动作后，作出返回
     * @param data
     * @return
     */
    public Object getResp(Map<String, String> data) {
        String msgType = data.get(CommConstants.MSG_TYPE);
        if (StringUtils.isBlank(msgType)) {
            return this.getDefaultErrorResp(data, "没有指定消息格式");
        }
        // 根据消息类型查找对应的处理器
        ReceptHandler receptHandler = receptHandlerMap.get(msgType);
        if (null == receptHandler) {
            return this.getDefaultErrorResp(data, "没有该种消息格式");
        }

        // 处理具体业务
        try {
            return receptHandler.invokeHandler(data);
        } catch (Exception e) {
            log.error("处理内部业务异常");
        }

        return this.getDefaultErrorResp(data, "服务内部异常");
    }

    /**
     * 出现异常后返回一个默认的错误文本结果
     * @param map  收到的map
     * @param content 返回的内容
     * @return
     */
    private Object getDefaultErrorResp(Map<String, String> map, String content) {
        TextMessage tm = new TextMessage(map, content);
        tm.setToUserName(map.get("FromUserName"));
        tm.setFromUserName(map.get("ToUserName"));
        tm.setCreateTime(System.currentTimeMillis()/1000+"");
        return tm;
    }

}
