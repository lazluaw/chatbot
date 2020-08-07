package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.proxy.Exit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamAnalysisController {
    @Autowired Serializer serializer;
    @Autowired Exit exit;
    @PostMapping("/v1/kakao/exam/kind")
    public Map<String, Object> examKind(@RequestBody Map<String, Object> jsonParams) {
        exit.exit(jsonParams);
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCode(@RequestBody Map<String, Object> jsonParams) {
        exit.exit(jsonParams);
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/user/analysis")
    public Map<String, Object> userFunc(@RequestBody Map<String, Object> jsonParams) {
        exit.exit(jsonParams);
        return serializer.examSer(jsonParams);
    }

    @PostMapping("/v1/kakao/admin/analysis")
    public Map<String, Object> adminFunc(@RequestBody Map<String, Object> jsonParams) {
        exit.exit(jsonParams);
        return serializer.examSer(jsonParams);
    }
 }