package com.chatbot.web.apis;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Reader {
    private String json;
    public String reader(JSONObject jsonData) {

        json = jsonData.toJSONString();
        return json;
    }
}