package com.chatbot.web.domains;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Lazy @Data
@Alias("chat")
@MappedTypes(LocalDate.class)
public class Chat {
    private int id, userCode;
    //chatDate 어떻게 처리할 지 고민
    private String chatBody, checkDate;
    private LocalDateTime insertDate, updateDate;
}