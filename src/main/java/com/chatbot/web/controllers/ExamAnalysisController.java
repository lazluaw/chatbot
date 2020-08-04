package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamAnalysisController {
//시험종류 - 과목종류 - user,admin
//모의고사 9과목이 지필과 동일해서 컬럼명을 추가할지 말지 고민중
    @Autowired
    Serializer serializer;
    @PostMapping("/v1/kakao/exam/kind")
    public Map<String, Object> examKind(@RequestBody Map<String, Object> jsonParams) {
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCode(@RequestBody Map<String, Object> jsonParams) throws IOException {
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/user/analysis")
    public Map<String, Object> userFunc(@RequestBody Map<String, Object> jsonParams) throws IOException {
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/admin/analysis")
    public Map<String, Object> adminFunc(@RequestBody Map<String, Object> jsonParams) {
        return serializer.examSer(jsonParams);
    }
 }