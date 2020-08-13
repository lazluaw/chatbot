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
    private int chatId, userCode, id;
    private String chatBody, name, userId, userPw;
    private LocalDateTime insertDate, updateDate;
}