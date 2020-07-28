package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Analyst;
import com.chatbot.web.conversions.Parser;
import com.chatbot.web.conversions.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/exam")
@ResponseBody
public class ExamAnalysisController {
    @Autowired Parser parser;
    @Autowired Serializer serializer;
    //test
    @Autowired Analyst analyst;


    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCodeCheck(@RequestBody Map<String, Object> jsonParams) throws IOException {
        parser.chatDataParser(jsonParams);
        analyst.examAnalysis();
        return serializer.subjectCodeSer();
    }

    @PostMapping("/v1/kakao/exam/analysis")
    public Map<String, Object> examAnalysis(@RequestBody Map<String, Object> jsonParams) throws IOException {
        parser.chatDataParser(jsonParams);
        parser.examAnalysisParser(jsonParams);
        return serializer.examAnalysisSer();
    }
 }