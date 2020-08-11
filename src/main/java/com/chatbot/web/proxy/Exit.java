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
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

//S로 저장된 DB 값을 삭제해야 함.
@Component @Lazy
@MappedTypes(LocalDate.class)
public class Exit {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ChatMapper chatMapper;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, arrObj;
    private JSONArray arr;
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
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            chat.setChatBody(chatBody);
            chatMapper.updateData(chat);
            chatHistory.setChatId(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);

            //Test
            String insertDate = chatMapper.selectDateList(chat.getId()).getInsertDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            System.out.println(insertDate);
            System.out.println(Integer.parseInt(insertDate));
            System.out.println(Integer.parseInt(insertDate)+30000);

            if(chat.getId() != 0 && chatBody.contains("종료")) {
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
                chatHistory.setChatBody("사용자종료");
                chatHistoryMapper.insertData(chatHistory);
                chat.setId(0);
                return obj;
            } else {
                insertDate = chatMapper.selectDateList(chat.getId()).getInsertDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
                String updateDate = chatMapper.selectDateList(chat.getId()).getUpdateDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
                SimpleDateFormat format = new SimpleDateFormat("yyyyHHddHHmm");
                Date date = new Date();
                String today = format.format(date);
                if (updateDate == null) {
                    if (Integer.parseInt(insertDate)+30000 == Integer.parseInt(today)) {
                        chatHistory.setChatKind("B");
                        chatHistory.setChatBody("시스템종료");
                        chatHistoryMapper.insertData(chatHistory);
                        chat.setId(0);
                        return null;
                    } else {
                        logger.error("시스템종료 insert ERROR");
                        return null;
                    }
                } else if(updateDate != null) {
                    if (Integer.parseInt(updateDate)+30000 == Integer.parseInt(today)) {
                        chatHistory.setChatKind("B");
                        chatHistory.setChatBody("시스템종료");
                        chatHistoryMapper.insertData(chatHistory);
                        chat.setId(0);
                        return null;
                    } else {
                        logger.error("시스템종료 update ERROR");
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