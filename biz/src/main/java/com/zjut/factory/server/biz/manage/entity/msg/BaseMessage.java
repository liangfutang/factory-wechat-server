package com.zjut.factory.server.biz.manage.entity.msg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter@Getter@ToString
@JacksonXmlRootElement(localName = "xml")
public class BaseMessage {

	@JacksonXmlCData
	@JacksonXmlProperty(localName = "ToUserName")
	private String toUserName;
	@JacksonXmlCData
	@JacksonXmlProperty(localName = "FromUserName")
	private String fromUserName;
//	@JacksonXmlCData
	@JacksonXmlProperty(localName = "CreateTime")
	private String createTime;
	/**
	 * 消息类型
	 */
	@JacksonXmlCData
	@JacksonXmlProperty(localName = "MsgType")
	private String msgType;

	public BaseMessage(Map<String, String> requestMap, String msgType) {
		this.toUserName=requestMap.get("FromUserName");
		this.fromUserName=requestMap.get("ToUserName");
		this.createTime=System.currentTimeMillis()/1000+"";
		this.msgType = msgType;
	}


}
