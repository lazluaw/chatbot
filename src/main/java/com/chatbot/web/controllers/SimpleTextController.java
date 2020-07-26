package com.chatbot.web.controllers;

import com.chatbot.web.conversions.Parser;
import com.chatbot.web.conversions.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@ResponseBody
public class SimpleTextController {
    @Autowired
    Serializer serializer;
    @Autowired Parser parser;
    @PostMapping("/v1/kakao/simple/text")
    public Map<String, Object> basicResponse(@RequestBody Map<String, Object> jsonParams) throws IOException {
        parser.chatDataParser(jsonParams);
        return serializer.simpleTextSer();
    }
}