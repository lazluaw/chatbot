package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    Serializer serializer;
    @PostMapping("/v1/kakao/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> jsonParams) {
        return serializer.loginSer(jsonParams);
    }
    @PostMapping("/v1/kakao/menu")
    public Map<String, Object> menu(@RequestBody Map<String, Object> jsonParams) {
        return serializer.menuSer(jsonParams);
    }
    @PostMapping("/v1/kakao/fallback")
    public Map<String, Object> fallback(@RequestBody Map<String, Object> jsonParams) {
        return serializer.fallbackSer(jsonParams);
    }
}