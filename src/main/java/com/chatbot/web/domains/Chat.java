package com.chatbot.web.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Lazy @Data
public class Chat {
    private Long id;
    private int chatIdx;
    @JsonProperty("chat_kind")
    private String chatKind;
    @JsonProperty("chat_body")
    private String chatBody;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}