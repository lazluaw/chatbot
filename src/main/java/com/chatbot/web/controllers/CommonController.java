package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired Serializers serializers;
    @PostMapping("/v1/kakao/fallback")
    public Map<String, Object> fallback(@RequestBody Map<String, Object> jsonParams) {
        return serializers.fallbackSer(jsonParams);
    }
}