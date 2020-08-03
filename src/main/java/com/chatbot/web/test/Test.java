package com.chatbot.web.test;

import com.chatbot.web.domains.*;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamMapper;
import com.chatbot.web.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class Test {
    @Autowired
    User user;
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    ChatHistoryMapper chatHistoryMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper mapper;
    private JSONParser parser;
    private String jsonStr;
    private JSONObject obj, obj1, obj2, obj3, obj4, obj5, obj6, arrObj;
    private JSONArray arr, arr2;
    private ValueOperations<String, Object> vop;

    //현재 테스트 : 아이디체크
    public void test(Map<String, Object> jsonParams) {
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
        try {
            vop = redisTemplate.opsForValue();
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonParams);
            JSONObject jsonPar = (JSONObject) parser.parse(jsonStr);
            JSONObject userRequest = (JSONObject) jsonPar.get("userRequest");
            String chatBody = (String) userRequest.get("utterance");

            JSONObject user = (JSONObject) userRequest.get("user");
            JSONObject properties = (JSONObject) user.get("properties");
            String botUserKey = (String) properties.get("botUserKey");
            vop.set(botUserKey, chat.getId());
            int userKey = (int) vop.get(botUserKey);

            if (chatBody.equals(userMapper.selectId())) {
                System.out.println("아이디 일치");
            } else {
                System.out.println("AUTH ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void examAnalysis() {
        System.out.println("---chat---");
        System.out.println("id: "+chat.getId());
        System.out.println("chatBody: "+chat.getChatBody());

        System.out.println("---chat history---");
        System.out.println(chatHistory.getChatId());
        System.out.println("chatKind: "+chatHistory.getChatKind());
        System.out.println(chatHistory.getChatBody());

        System.out.println("---exam---");
        System.out.println(exam.getExamKind());
        System.out.println(exam.getExamAnswer());
        System.out.println(exam.getExamChoice1());
        System.out.println(exam.getExamChoice2());
        System.out.println(exam.getExamChoice3());
        System.out.println(exam.getExamChoice4());
        System.out.println(exam.getExamChoice5());
        System.out.println(exam.getExamContent());
        System.out.println(exam.getExamDivision());
        System.out.println(exam.getExamNum());
        System.out.println(exam.getExamQuestion());
        System.out.println(exam.getSubejectCode());

        System.out.println("---exam_analysis---");
        System.out.println(examAnalysis.getExamKind());
        System.out.println(examAnalysis.getExamNum());
        System.out.println(examAnalysis.getSubjectCode());
        System.out.println(examAnalysis.getWrongAnswer());
    }
     */
}
