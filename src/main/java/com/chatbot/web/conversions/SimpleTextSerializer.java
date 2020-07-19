package com.chatbot.web.conversions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SimpleTextSerializer {
    private JSONObject obj, mostInnerObj, innerObj, arrObj;
    private JSONArray arr;

    public Map<String, Object> simpleTextSer() {
        obj = new JSONObject();
        mostInnerObj = new JSONObject();
        innerObj = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        obj.put("version", "2.0");

        mostInnerObj.put("text", "기본 텍스트");
        innerObj.put("simpleText", mostInnerObj);

        arr.add(innerObj);
        arrObj.put("outputs", arr);
        obj.put("template", arrObj);

        return obj;
    }
}
