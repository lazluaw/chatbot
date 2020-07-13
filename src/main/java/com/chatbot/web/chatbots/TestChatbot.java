package com.chatbot.web.chatbots;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TestChatbot {
    private String json;
    private JSONObject inner, outer, middleInner, finalInner;
    private JSONArray list;
    public TestChatbot() {
        inner = new JSONObject();
        outer = new JSONObject();
        list = new JSONArray();
        middleInner = new JSONObject();
        finalInner = new JSONObject();
    }
    public String test() {
        inner.put("text", "간단한 텍스트");
        outer.put("simpleText", inner);
        list.add(outer);
        middleInner.put("outputs", list);

        finalInner.put("template", middleInner);
        finalInner.put("version", "2.0");

        json = finalInner.toJSONString();
        inner.clear();
        outer.clear();
        list.clear();
        middleInner.clear();
        finalInner.clear();
        return json;
    }
}