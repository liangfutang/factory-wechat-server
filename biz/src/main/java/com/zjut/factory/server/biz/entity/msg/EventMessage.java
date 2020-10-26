package com.zjut.factory.server.biz.entity.msg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.zjut.factory.server.common.constants.CommConstants;

import java.util.Map;

@JacksonXmlRootElement(localName = "xml")
public class EventMessage extends BaseMessage{

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "Event")
    private String event;

    public EventMessage(Map<String, String> requestMap, String event) {
        super(requestMap, CommConstants.EVENT);
        this.event = event;
    }
}
