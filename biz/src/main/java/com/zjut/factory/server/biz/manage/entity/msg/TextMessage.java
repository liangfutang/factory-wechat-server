package com.zjut.factory.server.biz.manage.entity.msg;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.zjut.factory.server.common.constants.CommConstants;
import lombok.*;

import java.util.Map;

@Setter@Getter@ToString
@JacksonXmlRootElement(localName = "xml")
public class TextMessage extends BaseMessage {

	@JacksonXmlCData
	@JacksonXmlProperty(localName = "Content")
	private String content;
//	@JacksonXmlCData
	@JacksonXmlProperty(localName = "MsgId")
	private String msgId;

	public TextMessage(Map<String, String> requestMap, String content) {
		super(requestMap, CommConstants.TEXT);
		this.content = content;
		this.msgId = requestMap.get(CommConstants.MSG_ID);
	}

}
