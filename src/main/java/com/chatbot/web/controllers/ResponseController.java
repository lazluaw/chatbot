package com.chatbot.web.controllers;

import com.chatbot.web.conversions.MainSerialization;
import com.chatbot.web.conversions.ResponseSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
public class ResponseController {
    @ResponseBody
    @GetMapping("/response")
    public String basicResponse() throws JsonProcessingException {
        return MainSerialization.getResponseSerialization();
    }
}