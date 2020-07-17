package com.chatbot.web.controllers;

import com.chatbot.web.domains.Bicycle;
import com.chatbot.web.services.BicycleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BicycleController {
    @Autowired
    BicycleServiceImpl bicycleService;
    @Autowired
    Bicycle bicycle;

    @PostMapping("/reservation")
    public String fixReservation() {
        return bicycleService.addReservation();
    }

    @PostMapping("/reservation/check")
    public String checkReservation(@RequestBody Bicycle jsonData) {
        System.out.println(jsonData);
        return bicycleService.checkReservation();
    }

    @PostMapping("/reservation/complete")
    public String completeReservation() {
        return bicycleService.completeReservation();
    }
}