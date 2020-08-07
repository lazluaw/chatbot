package com.chatbot.web.proxy;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.Exam;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.Map;

//fallbackSer에서 기본적인 insert, update 담당

@Component @Lazy
public class Fallback {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, arrObj;
    private JSONArray arr, arr2;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String fallbackImg = "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg";
    public Map<String, Object> fallbackForm(Map<String, Object> jsonParams) {
        try {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            obj3 = new JSONObject();
            obj4 = new JSONObject();
            obj5 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            arr2 = new JSONArray();

            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String userInfo = userRequest.toString();

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("description", "챗봇을 이용하시려면 '로그인' 버튼을,\n종료하시려면 '종료' 버튼을 눌러주세요.");
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", fallbackImg);
            obj2.put("buttons", arr2);
            arr2.add(obj4);
            arr2.add(obj5);
            obj4.put("messageText", "로그인하기");
            obj4.put("label", "로그인하기");
            obj4.put("action", "message");
            obj5.put("messageText", "챗봇종료");
            obj5.put("label", "챗봇종료");
            obj5.put("action", "message");

            chatHistory.setChatId(chat.getId());
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatKind("B");
            chatHistory.setChatBody("폴백");
            chatHistoryMapper.insertData(chatHistory);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
