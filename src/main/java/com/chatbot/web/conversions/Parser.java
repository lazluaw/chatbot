package com.chatbot.web.conversions;

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
import java.util.Map;

@Service
public class Parser {
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ExamMapper examMapper;
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired Exam exam;
    public void chatDataParser(Map<String, Object> jsonParams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject bot = (JSONObject) obj.get("bot");
            String chatKind = (String) bot.get("id");
            JSONObject userRequest = (JSONObject) obj.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");
            String userInfo = userRequest.toString();

            if(chatKind != null) {
                chat.setChatKind("B");
                chat.setChatBody(chatBody);
                chatMapper.insertChat(chat);
                chatMapper.updateChat(chat);
                System.out.println("chat_id: " + chat.getId());
                System.out.println("chat_kind: " + chat.getChatKind());
                System.out.println("chat_body: " + chat.getChatBody());

                chatHistory.setUserInfo(userInfo);
                chatHistory.setChatBody(chatBody);
                chatHistoryMapper.insertHistory(chatHistory);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void examKindParser(Map<String, Object> jsonParams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequset = (JSONObject) obj.get("userRequest");
            String examKind = (String) userRequset.get("utterance");

            switch (examKind) {
                case "1학기 중간고사":
                    exam.setExamKind(1);
                    examMapper.insertExamData(exam);
                    break;
                case "1학기 기말고사":
                    exam.setExamKind(2);
                    examMapper.insertExamData(exam);
                    break;
                case "2학기 중간고사":
                    exam.setExamKind(3);
                    examMapper.insertExamData(exam);
                    break;
                case "2학기 기말고사":
                    exam.setExamKind(4);
                    examMapper.insertExamData(exam);
                    break;
                default:
                    System.out.println("ERROR");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}