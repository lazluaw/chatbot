package com.chatbot.web.mappers;

import com.chatbot.web.domains.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamMapper {
    public Exam selectList();
}