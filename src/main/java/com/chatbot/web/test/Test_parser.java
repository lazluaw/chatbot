package com.chatbot.web.test;

import com.chatbot.web.domains.Exam;
import com.chatbot.web.mappers.ExamMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Test_parser {
    @Autowired Exam exam;
    @Autowired ExamMapper examMapper;
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
