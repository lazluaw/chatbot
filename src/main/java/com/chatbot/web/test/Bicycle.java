package com.chatbot.web.test;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component @Lazy
@Data
public class Bicycle {
    private Long bicycleIdx;
    private Date bicycleDate;
}
