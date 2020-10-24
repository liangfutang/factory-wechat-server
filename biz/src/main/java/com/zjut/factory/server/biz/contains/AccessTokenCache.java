package com.zjut.factory.server.biz.contains;

import com.alibaba.fastjson.JSONObject;
import com.zjut.common.utils.HttpClientUtil;
import com.zjut.factory.server.biz.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存获取到的accesstoken
 */
@Slf4j
public class AccessTokenCache {

    private static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 缓存accesstoken，
     * key: appid
     * value: accesstoken
     */
    private static Map<String, AccessToken> accessTokenCache = new ConcurrentHashMap<>();

    /**
     * 获取有效AccessToken
     * @param appID
     * @param appsecret
     * @return
     */
    public static AccessToken getAccessToken(String appID, String appsecret) {
        AccessToken accessToken = accessTokenCache.get(appID);
        // 微信刷新次数限制，防止多刷。如果首次或者已经失效了
        if (accessToken == null || accessToken.isExpired()) {
            synchronized (AccessTokenCache.class) {
                if (accessTokenCache.get(appID) == null || accessToken.isExpired()) {
                    accessToken = refreshAccessToken(appID, appsecret);
                }
            }
        }
        return accessToken;
    }

    /**
     * 刷新最新的token
     * @param appID
     * @param appsecret
     * @return
     */
    private static AccessToken refreshAccessToken(String appID, String appsecret) {
        AccessToken result = null;
        String url = tokenUrl.replace("APPID", appID).replace("APPSECRET", appsecret);
        try {
            String httpGet = HttpClientUtil.getHttpGet(url, new HashMap<>(), null, 5000, 5000, 5000);
            if (StringUtils.isNotBlank(httpGet)) {
                JSONObject jsonObject = JSONObject.parseObject(httpGet);
                String accessTokenWechat = jsonObject.getString("access_token");
                long expiresInWechat = jsonObject.getLongValue("expires_in");
                if (StringUtils.isNotBlank(accessTokenWechat)) {
                    result = new AccessToken(accessTokenWechat, expiresInWechat);
                    accessTokenCache.put(appID, result);
                }
            }
        } catch (Exception e) {
            log.error("获取token失败:", e);
        }
        return result;
    }
}
