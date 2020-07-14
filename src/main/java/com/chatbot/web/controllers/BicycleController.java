package com.chatbot.web.controllers;

import com.chatbot.web.apis.Reader;
import com.chatbot.web.chatbots.BicycleChatbot;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BicycleController {
    @Autowired
    BicycleChatbot bicycle;
    @Autowired
    Reader reader;

    @PostMapping("/reservation")
    public String fixReservation() {
        return bicycle.addReservation();
    }

    @PostMapping("/reservation/check")
    public String checkReservation(@RequestBody JSONObject jsonData) {
        reader.reader(jsonData);
        return bicycle.checkReservation();
    }

    @PostMapping("/reservation/complete")
    public String completeReservation() {
        return bicycle.completeReservation();
    }
}