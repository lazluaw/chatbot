package com.chatbot.web.proxy;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component @Lazy
public class Fallback {
    @Autowired Serializer serializer;
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired RedisTemplate redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private ValueOperations<String, Object> vop;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public Map<String, Object> fallback(Map<String, Object> jsonParams) {
        try {
            mapper = new ObjectMapper();
            parser = new JSONParser();
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            if (chat.getChatId() != 0) {
                logger.info("로그인O fallback");
                vop.set("title", "무슨 의미인지 모르겠어요!");
                vop.set("description", "챗봇을 이용하시려면 '메뉴' 버튼을,\n종료하시려면 '종료' 버튼을 눌러주세요.");
                vop.set("img", "https://i.pinimg.com/564x/fe/b7/2d/feb72dfa16dfd4781c26b550edd88255.jpg");
                vop.set("firstMessage", "메뉴로 이동");
                if (vop.get("adminCode").equals(String.valueOf(chat.getUserCode()))) {
                    chat.setUserCode(Integer.valueOf(vop.get("adminCode").toString()));
                } else if (vop.get("userCode").equals(String.valueOf(chat.getUserCode()))) {
                    chat.setUserCode(Integer.valueOf(vop.get("userCode").toString()));
                }
                chat.setChatBody(chatBody);
                chatMapper.insertData(chat);
                chatHistory.setChatId(chat.getChatId());
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatKind("B");
                chatHistory.setChatBody("로그인후폴백");
                chatHistoryMapper.insertData(chatHistory);
                return serializer.addJson("bas", "basBut");
            } else if (chatBody.contains("로그인") || chatBody.equals(vop.get("adminLogin")) || chatBody.equals(vop.get("userLogin"))) {
                logger.info("로그인O 중복");
                vop.set("title", "이미 로그인하였습니다!");
                vop.set("description", "이미 로그인이 되어 있는 상태입니다 :)\n어디로 이동할까요?");
                vop.set("img", "https://i.pinimg.com/564x/f4/62/c9/f462c9fc876243221c6ee36c3fb97b32.jpg");
                vop.set("firstMessage", "메뉴");

                chat.setChatBody(chatBody);
                chat.setUserCode(chat.getUserCode());
                chatMapper.insertData(chat);

                chatHistory.setChatId(chat.getChatId());
                chatHistory.setChatKind("C");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(chatBody);
                chatHistoryMapper.insertData(chatHistory);

                chatHistory.setChatKind("B");
                chatHistory.setChatBody("로그인중복");
                chatHistoryMapper.insertData(chatHistory);
                return serializer.addJson("bas", "basBut");
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
            vop = redisTemplate.opsForValue();
            logger.info("로그인실패 fallback");
            vop.set("text", "로그인에 실패하였습니다.\n다시 한 번 정확히 입력해 주세요.\n(형식 : 아이디, 비밀번호)");
            return serializer.addJson("sim", null);
        } catch (Exception e) {
            logger.info("로그인 실패 logic ERROR");
            e.printStackTrace();
            return null;
        }
    }
}
