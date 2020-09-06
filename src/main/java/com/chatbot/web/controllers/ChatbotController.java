package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("")
public class ChatbotController {
    @Autowired Serializer serializer;
    @PostMapping("/v1/kakao/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/fallback")
    public Map<String, Object> fallback(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/exit")
    public Map<String, Object> exit(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/menu")
    public Map<String, Object> menu(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/streaming")
    public Map<String, Object> stream(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/attendance")
    public Map<String, Object> attendance(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/exam/kind")
    public Map<String, Object> examKind(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCode(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/wronganswernote")
    public Map<String, Object> wrongAnswerNote(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/analysis/criteria")
    public Map<String, Object> analysisCriteria(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }

    @PostMapping("/v1/kakao/analysis")
    public Map<String, Object> analysis(@RequestBody Map<String, Object> jsonParams) { return serializer.checkSer(jsonParams); }
}