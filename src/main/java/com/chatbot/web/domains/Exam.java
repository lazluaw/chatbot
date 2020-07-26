package com.chatbot.web.domains;

import lombok.Data;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Lazy @Data
@MappedTypes(LocalDate.class)
public class Exam {
    private Long id;
    private int examKind, examNum;
    private String examQuestion, examDivision, examChoice1, subejectCode,
            examChoice2, examChoice3, examChoice4, examChoice5, examAnswer, examContent;
    private LocalDateTime insertDate;
}