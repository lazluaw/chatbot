package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Serializer;
import com.chatbot.web.proxy.Exit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("")
public class CommonController {
    @Autowired
    Serializer serializer;
    @Autowired
    Exit exit;

    @PostMapping("/v1/kakao/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> jsonParams) {
        return serializer.loginSer(jsonParams);
    }

    @PostMapping("/v1/kakao/fallback")
    public Map<String, Object> fallback(@RequestBody Map<String, Object> jsonParams) {
        return serializer.fallbackSer(jsonParams);
    }

    @PostMapping("/v1/kakao/exit")
    public Map<String, Object> exit(@RequestBody Map<String, Object> jsonParams) {
        return exit.userExit(jsonParams);
    }

    @PostMapping("/v1/kakao/menu")
    public Map<String, Object> menu(@RequestBody Map<String, Object> jsonParams) {
        return serializer.menuSer(jsonParams);
    }

    @PostMapping("/v1/kakao/stream")
    public Map<String, Object> stream(@RequestBody Map<String, Object> jsonParams) {
        return serializer.classSer(jsonParams);
    }

    @PostMapping("/v1/kakao/admin/attendance")
    public Map<String, Object> adminAt(@RequestBody Map<String, Object> jsonParams) {
        return serializer.classSer(jsonParams);
    }

    @PostMapping("/v1/kakao/user/attendance")
    public Map<String, Object> userAt(@RequestBody Map<String, Object> jsonParams) {
        return serializer.classSer(jsonParams);
    }
}