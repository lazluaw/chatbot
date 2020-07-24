package com.chatbot.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BicycleController {
    @Autowired
    BicycleSerializer bicycleService;
    @Autowired
    Bicycle bicycle;

    @PostMapping("/v1/kakao/reservation")
    public String fixReservation() {
        return bicycleService.addReservation();
    }

    @PostMapping("/v1/kakao/reservation/check")
    public String checkReservation(@RequestBody Bicycle jsonData) {
        System.out.println(jsonData);
        return bicycleService.checkReservation();
    }

    @PostMapping("/v1/kakao/reservation/complete")
    public String completeReservation() {
        return bicycleService.completeReservation();
    }
}