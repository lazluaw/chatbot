package com.chatbot.web.controllers;

import com.chatbot.web.chatbots.FixBicycleChatbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BicycleController {
    @Autowired
    FixBicycleChatbot testChatbot;

    @PostMapping("/reservation")
    public String fixReservation() {
        return testChatbot.addReservation();
    }

    @PostMapping("/reservation/check")
    public String checkReservation() {
        return testChatbot.checkReservation();
    }

    @PostMapping("/reservation/complete")
    public String completeReservation() {
        return testChatbot.completeReservation();
    }

}