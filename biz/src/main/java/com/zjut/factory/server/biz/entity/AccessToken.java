package com.zjut.factory.server.biz.entity;

import lombok.Data;

/**
 * 获取到的某个微信公众号token
 */
@Data
public class AccessToken {

    /**
     * 有效token
     */
    private String accessToken;
    /**
     * 失效时间
     */
    private long expireTime;

    public AccessToken(String accessToken, long expireTime) {
        this.accessToken = accessToken;
        this.expireTime = System.currentTimeMillis() + expireTime*1000;
    }

    /**
     * 判断当前accesstoken是否失效
     * @return
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }
}
