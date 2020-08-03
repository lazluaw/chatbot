package com.chatbot.web.proxy;

import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component @Lazy
public class Fallback {
    @Autowired ChatHistory chatHistory;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, arrObj;
    private JSONArray arr, arr2;
    public Map<String, Object> fallbackForm() {
        obj = new JSONObject();
        obj1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        arrObj = new JSONObject();
        arr = new JSONArray();
        arr2 = new JSONArray();

        obj.put("version", "2.0");
        obj.put("template", arrObj);
        arrObj.put("outputs", arr);
        arr.add(obj1);
        obj1.put("basicCard", obj2);
        obj2.put("description", "무슨 뜻인지 이해하기 힘들어요.\n챗봇을 이용하시려면 '로그인' 버튼을,\n종료하시려면 '종료' 버튼을 눌러주세요.");
        obj2.put("thumbnail", obj3);
        obj3.put("imageUrl", "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
        obj2.put("buttons", arr2);
        arr2.add(obj4);
        arr2.add(obj5);
        obj4.put("messageText", "본인인증");
        obj4.put("label", "로그인하기");
        obj4.put("action", "message");
        obj5.put("messageText", "챗봇종료");
        obj5.put("label", "챗봇종료");
        obj5.put("action", "message");
        chatHistory.setChatKind("B");
        chatHistory.setChatBody("폴백메세지");
        chatHistoryMapper.insertData(chatHistory);
        return obj;
    }
}
