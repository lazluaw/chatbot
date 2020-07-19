package com.chatbot.web.controllers;

import com.chatbot.web.services.ResponseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/chatbot")
public class ResponseController {
    @Autowired
    ResponseServiceImpl responseService;
    @ResponseBody
    @GetMapping("/response")
    public String basicResponse() throws IOException {
        return responseService.serializer();
    }
}