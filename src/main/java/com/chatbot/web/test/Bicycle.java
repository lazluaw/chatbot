package com.chatbot.web.test;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component @Lazy
@Data
public class Bicycle {
    private Long bicycleIdx;
    private Date bicycleDate;
}
