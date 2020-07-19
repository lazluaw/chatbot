package com.chatbot.web.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Lazy @Data
public class ChatHistory {
    private Long id;
    private int historyIdx;
    private String chatBody;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}