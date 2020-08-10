package com.chatbot.web.proxy;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.MappedTypes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component @Lazy
@MappedTypes(LocalDate.class)
public class Exit {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ChatMapper chatMapper;
    @Autowired RedisTemplate redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, arrObj;
    private JSONArray arr;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String exitImg = "https://i.pinimg.com/564x/37/a2/6b/37a26b5b2b879c5280cbe4457d0d4649.jpg";
    public Map<String, Object> exit(Map<String, Object> jsonParams) {
        try {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            obj3 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            //test
            System.out.println(vop.get("loginSuccess"));

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");

            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            if(chatBody.contains("종료")) {
                obj.put("version", "2.0");
                obj.put("template", arrObj);
                arrObj.put("outputs", arr);
                arr.add(obj1);
                obj1.put("basicCard", obj2);
                obj2.put("title", "챗봇이 종료됩니다. 감사합니다.");
                obj2.put("description", "항상 앞으로 나아가는 피클 서비스, 피클봇이 되겠습니다.");
                obj2.put("thumbnail", obj3);
                obj3.put("imageUrl", exitImg);
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("챗봇종료");
                chatHistoryMapper.insertData(chatHistory);

                vop.set(botUserKey, 0);
                chat.setId(0);
                return obj;
            } else {
                //test: 출력을 확인하기 위함
                System.out.println(chatMapper.selectDateList(chat.getId()).toString());
                LocalDateTime insertDate = chatMapper.selectDateList(chat.getId()).getInsertDate();
                LocalDateTime updateDate = chatMapper.selectDateList(chat.getId()).getUpdateDate();
                if (updateDate == null) {
                    //insertDate 72times
                    chatHistory.setChatKind("B");
                    chatHistory.setChatBody("챗봇종료");
                    chatHistoryMapper.insertData(chatHistory);

                    vop.set(botUserKey, 0);
                    chat.setId(0);
                    return null;
                } else if(updateDate != null) {
                    //updateDate 72times
                    chatHistory.setChatKind("B");
                    chatHistory.setChatBody("챗봇종료");
                    chatHistoryMapper.insertData(chatHistory);

                    vop.set(botUserKey, 0);
                    chat.setId(0);
                    return null;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exit ERROR");
            return null;
        }
    }
}