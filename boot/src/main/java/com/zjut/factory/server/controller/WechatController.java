package com.zjut.factory.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@Slf4j
public class WechatController {

    /**
     * 绑定微信公众号时在微信端配置的token(随机设置的，此处需与微信端的保持一致)
     */
    private final String TOKEN = "abc";

    /**
     * 验证消息的确来自微信服务器,初次绑定后，微信端需要向后端发送验证请求
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/wechat/officialaccounts/init")
    public String initPublicServer(HttpServletRequest request, HttpServletResponse response) {
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        log.info("微信公众号绑定提交初始化参数signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)) {
            log.info("缺少必要的参数导致没法验证通过");
            return "接入失败";
        }

        // 1）将token、timestamp、nonce三个参数进行字典序排序
        String[] arrays = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(arrays);
        String str = arrays[0] + arrays[1] + arrays[2];
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String sha1Hex = DigestUtils.sha1Hex(str);
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (sha1Hex.equals(signature)) {
            log.info("绑定提供通过");
            return echostr;
        }

        return "接入失败";
    }
}
