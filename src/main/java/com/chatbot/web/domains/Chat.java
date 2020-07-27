package com.chatbot.web.domains;

import lombok.Data;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Lazy @Data
@MappedTypes(LocalDate.class)
public class Chat {
    private int id;
    private int userCode;
    private String chatKind, chatBody;
    private LocalDateTime insertDate, updateDate;
}