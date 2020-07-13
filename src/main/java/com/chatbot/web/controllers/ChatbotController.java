package com.chatbot.web.controllers;

import com.chatbot.web.chatbots.TestChatbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ChatbotController {
    @Autowired
    TestChatbot testChatbot;

    @PostMapping("")
    public String test() {
        return testChatbot.test();
    }
}