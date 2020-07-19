package com.chatbot.web.controllers;

import com.chatbot.web.conversions.ChatParser;
import com.chatbot.web.conversions.SimpleTextSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class SimpleTextController {
    @Autowired
    SimpleTextSerializer responseService;
    @Autowired
    ChatParser simpleTextBotChatParser;
    @ResponseBody
    @PostMapping("/v1/kakao/simple/text")
    public Map<String, Object> basicResponse(@RequestBody Map<String, Object> jsonParams) throws IOException {
        simpleTextBotChatParser.dataParser(jsonParams);
        return responseService.simpleTextSer();
    }
}