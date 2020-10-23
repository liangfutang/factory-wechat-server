package com.zjut.factory.server.biz.manage.entity.msg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Map;

@Data
@JacksonXmlRootElement(localName = "xml")
public abstract class BaseMessage {

	@JacksonXmlCData
	@JacksonXmlProperty(localName = "ToUserName")
	private String toUserName;
	@JacksonXmlCData
	@JacksonXmlProperty(localName = "FromUserName")
	private String fromUserName;
	@JacksonXmlProperty(localName = "CreateTime")
	private String createTime;
	/**
	 * 消息类型
	 */
	@JacksonXmlCData
	@JacksonXmlProperty(localName = "MsgType")
	private String msgType;

	BaseMessage(Map<String, String> requestMap, String msgType) {
		this.toUserName=requestMap.get("FromUserName");
		this.fromUserName=requestMap.get("ToUserName");
		this.createTime=System.currentTimeMillis()/1000+"";
		this.msgType = msgType;
	}


}
