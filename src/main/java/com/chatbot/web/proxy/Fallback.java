package com.chatbot.web.proxy;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@Component @Lazy
public class Fallback {
    @Autowired Serializer serializer;
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired UserMapper userMapper;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired RedisTemplate redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, arrObj;
    private JSONArray arr, arr2;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String fallbackImg = "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg";
    public Map<String, Object> fallback(Map<String, Object> jsonParams) {
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
            vop = redisTemplate.opsForValue();

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("basicCard", obj2);
            if (vop.get("loginSuccess") == "S") {
                logger.info("로그인O fallback");
                obj2.put("title", "무슨 의미인지 모르겠어요!");
                obj2.put("description", "챗봇을 이용하시려면 '메뉴' 버튼을,\n종료하시려면 '종료' 버튼을 눌러주세요.");
                obj2.put("thumbnail", obj3);
                obj3.put("imageUrl", fallbackImg);
                obj2.put("buttons", arr2);
                arr2.add(obj4);
                arr2.add(obj5);
                obj4.put("messageText", "메뉴로 이동");
                obj4.put("label", "메뉴");
                obj4.put("action", "message");
                obj5.put("messageText", "챗봇종료");
                obj5.put("label", "챗봇종료");
                obj5.put("action", "message");

                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
                JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
                JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
                String chatBody = (String) userRequest.get("utterance");
                String userInfo = userRequest.toString();

                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    chat.setUserCode(Integer.valueOf(vop.get("adminCode").toString()));
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    chat.setUserCode(Integer.valueOf(vop.get("userCode").toString()));
                }
                chat.setChatBody(chatBody);
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getId());
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatKind("S");
                chatHistory.setChatBody("로그인후폴백");
                chatHistoryMapper.insertData(chatHistory);
                return obj;
            } else {
                return serializer.loginSer();
            }
        } catch (Exception e) {
            logger.error("fallback ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> failLogin() {
        try {
            logger.info("로그인실패 fallback");
            obj = new JSONObject();
            obj1 = new JSONObject();
            obj2 = new JSONObject();
            arrObj = new JSONObject();
            arr = new JSONArray();

            obj.put("version", "2.0");
            obj.put("template", arrObj);
            arrObj.put("outputs", arr);
            arr.add(obj1);
            obj1.put("simpleText", obj2);
            obj2.put("text", "로그인에 실패하였습니다.\n다시 한 번 정확히 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return obj;
        } catch (Exception e) {
            logger.info("로그인 실패 logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}
