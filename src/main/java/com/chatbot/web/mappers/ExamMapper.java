package com.chatbot.web.mappers;

import com.chatbot.web.domains.Exam;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamMapper {
    public List<Exam> selectList();
}