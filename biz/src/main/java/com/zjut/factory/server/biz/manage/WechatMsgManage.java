package com.zjut.factory.server.biz.manage;

import com.zjut.factory.server.biz.manage.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来处理接收到的微信公众号消息，并作出返回动作
 */
@Component
@Slf4j
public class WechatMsgManage {

    /**
     * 解析传进来的消息，并作出相应的动作后，作出返回
     * @param data
     * @return
     */
    public Object getResp(String data) {
        Map<String,String> map = this.parseWechatMsg(data);
        log.info("解析完的map:" + map);
        String msgType = map.get(CommConstants.MSG_TYPE);
        switch (msgType){
            case "text":
                return dealText(map);
            case "image":
                
            case "voice":

                break;
            case "video":

                break;
            case "shortvideo":

                break;
            case "location":

                break;
            case "link":

                break;
            case "event":

                break;
        }

        return null;
    }

    /**
     *处理文本消息
     * @param map
     * @return
     */
    private Object dealText(Map<String, String> map) {
        TextMessage tm = new TextMessage(map, "点击<a href=\"www.baidu.com\">这里</a>登录");
        tm.setToUserName(map.get("FromUserName"));
        tm.setFromUserName(map.get("ToUserName"));
        tm.setCreateTime(System.currentTimeMillis()/1000+"");
        return tm;
    }

    /**
     * 解析公众号传进来的xml消息
     * @return
     */
    private Map<String,String> parseWechatMsg(String data) {
        Map<String,String> map = new HashMap<>(8);

        Document document = null;
        try {
            document = DocumentHelper.parseText(data);
        } catch (Exception e) {
            log.error("解析传进来的xml异常", e);
        }
        if (document == null) {
            return map;
        }
        Element root = document.getRootElement();
        if (root == null) {
            return map;
        }
        List<Element> elements = root.elements();
        for(Element e:elements){
            map.put(e.getName(), e.getStringValue());
        }
        return map;
    }
}
