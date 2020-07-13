/* template 준비 중
package com.chatbot.web.apis;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JSONTemplate {
    private String key, value, json;
    private JSONObject inner, outer;
    public JSONTemplate() {
        inner = new JSONObject();
        outer = new JSONObject();
    }
    public void addJSON(String key, String value) {
        inner.put(key, value);
    }
    public void
    public void jsonTemplate() {
        inner.put("version", "2.0");
        inner.put(addJSON());
        outer.put("template", outer.put("outputs", inner));
        json = outer.toJSONString();
    }
}
*/