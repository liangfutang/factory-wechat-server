package com.zjut.factory.server.biz.manage;

import com.zjut.factory.server.biz.manage.beanhandler.invokemsg.ReceptHandler;
import com.zjut.factory.server.biz.manage.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
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

//        switch (msgType){
//            case "text":
//                return dealText(data);
//            case "image":
//
//            case "voice":
//
//                break;
//            case "video":
//
//                break;
//            case "shortvideo":
//
//                break;
//            case "location":
//
//                break;
//            case "link":
//
//                break;
//            case "event":
//
//                break;
//            default:
//                return this.getDefaultErrorResp(data, "没有该种消息格式");
//        }

        return this.getDefaultErrorResp(data, "服务内部异常");
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
