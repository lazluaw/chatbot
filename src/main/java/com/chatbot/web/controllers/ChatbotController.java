package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.proxy.Exit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("")
public class ChatbotController {
    @Autowired Serializer serializer;
    @Autowired Exit exit;

    @PostMapping("/v1/kakao/login")
    public Map<String, Object> login() { return serializer.loginSer(); }

    @PostMapping("/v1/kakao/fallback")
    public Map<String, Object> fallback(@RequestBody Map<String, Object> jsonParams) { return serializer.controlSer(jsonParams); }

    @PostMapping("/v1/kakao/exit")
    public Map<String, Object> exit(@RequestBody Map<String, Object> jsonParams) {
        return exit.exit(jsonParams);
    }

    @PostMapping("/v1/kakao/menu")
    public Map<String, Object> menu(@RequestBody Map<String, Object> jsonParams) { return serializer.menuSer(jsonParams); }

    @PostMapping("/v1/kakao/stream")
    public Map<String, Object> stream(@RequestBody Map<String, Object> jsonParams) { return serializer.classSer(jsonParams); }

    @PostMapping("/v1/kakao/admin/attendance")
    public Map<String, Object> adminAt(@RequestBody Map<String, Object> jsonParams) { return serializer.classSer(jsonParams); }

    @PostMapping("/v1/kakao/user/attendance")
    public Map<String, Object> userAt(@RequestBody Map<String, Object> jsonParams) { return serializer.classSer(jsonParams); }

    @PostMapping("/v1/kakao/exam/kind")
    public Map<String, Object> examKind(@RequestBody Map<String, Object> jsonParams) { return serializer.examSer(jsonParams); }

    @PostMapping("/v1/kakao/subject/code")
    public Map<String, Object> subjectCode(@RequestBody Map<String, Object> jsonParams) { return serializer.examSer(jsonParams); }

    @PostMapping("/v1/kakao/user/analysis")
    public Map<String, Object> userFunc(@RequestBody Map<String, Object> jsonParams) { return serializer.examSer(jsonParams); }

    @PostMapping("/v1/kakao/admin/analysis")
    public Map<String, Object> adminFunc(@RequestBody Map<String, Object> jsonParams) { return serializer.examSer(jsonParams); }
}