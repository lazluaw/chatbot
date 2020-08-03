package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired Serializers serializers;
    @PostMapping("/v1/kakao/id")
    public Map<String, Object> authId(@RequestBody Map<String, Object> jsonParams) {
        System.out.println(jsonParams);
        return serializers.authSer(jsonParams);
    }
    @PostMapping("/v1/kakao/pw")
    public Map<String, Object> authPw(@RequestBody Map<String, Object> jsonParams) {
        System.out.println(jsonParams);
        return serializers.authSer(jsonParams);
    }
}