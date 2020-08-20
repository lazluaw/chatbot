package com.chatbot.web.proxy;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.MappedTypes;
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
import java.util.Map;

@Component @Lazy
@MappedTypes(LocalDate.class)
public class Exit {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ChatMapper chatMapper;
    @Autowired Serializer serializer;
    @Autowired RedisTemplate redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ValueOperations<String, Object> vop;
    public Map<String, Object> exit(Map<String, Object> jsonParams) {
        try {
            vop = redisTemplate.opsForValue();
            mapper = new ObjectMapper();
            parser = new JSONParser();
            if (chat.getChatId() == 0) {
                vop.set("text", "피클봇이 종료됩니다. 감사합니다.");
                return serializer.addJson("sim", null);
            } else if (chat.getChatId() != 0) {
                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
                JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
                JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
                String chatBody = (String) userRequest.get("utterance");
                String userInfo = userRequest.toString();
                chat.setChatBody(chatBody);
                chatMapper.updateData(chat);
                chatHistory.setChatId(chat.getChatId());
                chatHistory.setChatKind("C");
                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(chatBody);
                chatHistoryMapper.insertData(chatHistory);

                vop.set("title", "피클봇이 종료됩니다. 감사합니다.");
                vop.set("description", "항상 앞으로 나아가는 피클 서비스, 피클봇이 되겠습니다.");
                vop.set("img", "https://i.pinimg.com/564x/37/a2/6b/37a26b5b2b879c5280cbe4457d0d4649.jpg");

                chatHistory.setChatKind("B");
                chatHistory.setChatBody("사용자종료");
                chatHistoryMapper.insertData(chatHistory);
                chat.setChatId(0);
//                System.out.println(Integer.parseInt(chatMapper.selectList().getInsertDateDiffCnt().toString()) == -3);
                //null이면 안나옴
                return serializer.addJson("bas", "");
            } else {
                if (chatMapper.selectList().getChatBody() != "챗봇종료") {
                    if (chatMapper.selectList().getUpdateDate() == null) {
                        if (Integer.parseInt(chatMapper.selectList().getInsertDateDiffCnt().toString()) == -3) {
                            chatHistory.setChatKind("B");
                            chatHistory.setChatBody("시스템종료");
                            chatHistoryMapper.insertData(chatHistory);
                            chat.setChatId(0);
                            return null;
                        } else {
                            logger.error("시스템종료 insert ERROR");
                            return null;
                        }
                    } else if (chatMapper.selectList().getUpdateDate() != null) {
                        if (Integer.parseInt(chatMapper.selectList().getUpdateDateDiffCnt().toString()) == -3) {
                            chatHistory.setChatKind("B");
                            chatHistory.setChatBody("시스템종료");
                            chatHistoryMapper.insertData(chatHistory);
                            chat.setChatId(0);
                            return null;
                        } else {
                            logger.error("시스템종료 update ERROR");
                            return null;
                        }
                    } else {
                        logger.error("시스템종료 3일 측정 ERROR");
                        return null;
                    }
                } else {
                    logger.error("시스템종료 ERROR");
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exit ERROR");
            return null;
        }
    }
}