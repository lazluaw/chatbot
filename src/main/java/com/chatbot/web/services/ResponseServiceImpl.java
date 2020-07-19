package com.chatbot.web.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl {
    private JSONObject obj, innerObj1, innerObj2;
    private JSONArray arr;

    public String serializer() {
        obj.put("version", "2.0");

        innerObj1.put("text", "기본 텍스트");
        innerObj2.put("simpleText", innerObj1);
        
        arr.add(innerObj2);
        obj.put("outputs", arr);

        return obj.toString();
    }
}
