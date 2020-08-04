package com.chatbot.web.proxy;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component @Lazy
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
    public void systemExit(Map<String, Object> jsonParams) {
        try {
            //test: 인서트, 업데이트 데이트
            System.out.println("insert: "+chatMapper.selectIDate());
            System.out.println("update: "+chatMapper.selectUDate());
//            System.out.println("chatId: "+vop.get(botUserKey));

            if (chatMapper.selectUDate() == null) {
                System.out.println("insertDate: " + chatMapper.selectIDate());
                // == null : insert_date -> 72시간 경과 여부 (259200초)
//                if(chatMapper.selectIDate() )
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("챗봇종료");
                chatHistoryMapper.insertData(chatHistory);

//                vop.set(botUserKey, 0);
                chat.setId(0);
            } else if (chatMapper.selectUDate() != null) {
                System.out.println("updateDate: " + chatMapper.selectUDate());
                // != null : update_date -> 72시간 경과 여부 (259200초)
//                if(chatMapper.updateData())
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("챗봇종료");
                chatHistoryMapper.insertData(chatHistory);

//                vop.set(botUserKey, 0);
                chat.setId(0);
            } else {
                System.out.println("systemExit 로직 ERROR catch print");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("systemExit 클래스 ERROR");
        }
    }

    public Map<String, Object> userExit(Map<String, Object> jsonParams) {
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

            //test: 인서트, 업데이트 데이트
            System.out.println("insert: "+chatMapper.selectIDate());
            System.out.println("update: "+chatMapper.selectUDate());

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

            chat.setChatBody(chatBody);
            if (userKey == 0) {
                System.out.println("exit insert");
                chatMapper.insertData(chat);
            } else if (userKey != 0) {
                System.out.println("exit update");
                chatMapper.updateData(chat);
            } else {
                System.out.println("fallback ERROR");
            }
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            obj2.put("title", "챗봇이 종료됩니다. 감사합니다.");
            obj2.put("description", "항상 앞으로 나아가는 피클 서비스, 피클봇이 되겠습니다.");
            obj2.put("thumbnail", obj3);
            obj3.put("imageUrl", "https://i.pinimg.com/564x/37/a2/6b/37a26b5b2b879c5280cbe4457d0d4649.jpg");
            chatHistory.setChatKind("B");
            chatHistory.setChatBody("챗봇종료");
            chatHistoryMapper.insertData(chatHistory);

            vop.set(botUserKey, 0);
            chat.setId(0);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}