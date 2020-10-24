package com.zjut.factory.server.biz.beanhandler.invokemsg;

import com.alibaba.fastjson.JSONObject;
import com.zjut.common.constants.Constants;
import com.zjut.common.utils.HttpClientUtil;
import com.zjut.factory.server.biz.entity.msg.BaseMessage;
import com.zjut.factory.server.biz.entity.msg.TextMessage;
import com.zjut.factory.server.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 文本类型消息处理器
 */
@Component(CommConstants.TEXT)
@Slf4j
public class TextMessageHandler implements ReceptHandler {
    @Override
    public Object invokeHandler(Map<String, String> data) {
        // 入参中取出公众号传过来的信息内容
        String contentStr = data.get(CommConstants.CONTENT);
        String content = StringUtils.isBlank(contentStr) ? "你好" : contentStr;
        // 拿出回复
        String answer = this.chat(content);
        log.info("拿到的回复:" + answer);
        return this.getResp(data, answer);
    }

    /**
     * 根据进来的信息查出回复
     * @param fromMessage
     * @return
     */
    private String chat(String fromMessage) {
        String url ="http://op.juhe.cn/robot/index";
        Map params = new HashMap();
        params.put("key","1fec136dbd19f44743803f89bd55ca62");//您申请到的本接口专用的APPKEY
        params.put("info","空");//要发送给机器人的内容，不要超过30个字符
        params.put("dtype","");//返回的数据的格式，json或xml，默认为json
        params.put("loc","");//地点，如北京中关村
        params.put("lon","");//经度，东经116.234632（小数点后保留6位），需要写为116234632
        params.put("lat","");//纬度，北纬40.234632（小数点后保留6位），需要写为40234632
        params.put("userid","");//1~32位，此userid针对您自己的每一个用户，用于上下文的关联

        try {
            String httpGet = HttpClientUtil.getHttpGet(url, params, null, 5000, 5000, 5000);
            log.info("请求智能回复的结果:" + httpGet);
            if (StringUtils.isNotBlank(httpGet)) {
                JSONObject jsonObject = JSONObject.parseObject(httpGet);
                Integer errorCode = jsonObject.getInteger(CommConstants.ERROR_CODE);
                if (0==errorCode) {
                    JSONObject result = jsonObject.getJSONObject(Constants.RESULT);
                    if (null != result) {
                        String text = result.getString(CommConstants.TEXT);
                        if (StringUtils.isNotBlank(text)) {
                            return text;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取只能回复异常", e);
        }
        return "哎呀，好糗啊";
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
