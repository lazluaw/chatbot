package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Parser;
import com.chatbot.web.conversions.SimpleTextSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/exam")
@ResponseBody
public class ExamAnalysisController {
    @Autowired Parser parser;
    @Autowired SimpleTextSerializer simpleTextSerializer;
    @PostMapping("/v1/kakao/exam/kind")
    public Map<String, Object> examKindCheck(@RequestBody Map<String, Object> jsonParams) throws IOException {
        parser.chatDataParser(jsonParams);
        parser.examKindParser(jsonParams);
        return simpleTextSerializer.simpleTextSer();
    }
    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCodeCheck(@RequestBody Map<String, Object> jsonParams) throws IOException {
        parser.chatDataParser(jsonParams);
        return null;
    }
 }