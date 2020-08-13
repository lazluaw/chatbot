/*
package com.chatbot.web.test;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.Exam;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

@Service
public class Parser {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired Exam exam;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ExamMapper examMapper;
    public void chatDataParser(Map<String, Object> jsonParams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
        JSONParser parser = new JSONParser();
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //날짜 포맷 지정

        try {
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) obj.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
//            JSONObject block = (JSONObject) userRequest.get("block");
//            String name = (String) block.get("name"); //block's name
            String userInfo = userRequest.toString();

            String uDate = sDate.format(new Date()); //날짜 지정
            Date toDate = sDate.parse(uDate);

            chat.setChatBody(chatBody);

            //update 로직 변경
            if(chat.getInsertDate() != null){
                chatMapper.insertData(chat);
            }else{
                chat.setCheckDate(uDate);
                chatMapper.updateData(chat);
            }

            chatHistory.setChatId(chat.getId());
            System.out.println(chat.getId());
            chatHistory.setChatKind("C");
            chatHistory.setUserInfo(userInfo);
            chatHistory.setChatBody(chatBody);
            chatHistoryMapper.insertData(chatHistory);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public void examAnalysisParser(Map<String, Object> jsonData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
        JSONParser parser = new JSONParser();

        try {
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) obj.get("userRequest");
            String subjectCode = (String) userRequest.get("utterance");
//            System.out.println("subjectCode: " + subjectCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 */