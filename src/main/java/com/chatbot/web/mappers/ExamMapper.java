package com.chatbot.web.mappers;

import com.chatbot.web.domains.Exam;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface ExamMapper {
    public ArrayList<Exam> selectList(Exam exam);
}