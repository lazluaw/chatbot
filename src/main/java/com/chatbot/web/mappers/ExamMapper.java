package com.chatbot.web.mappers;

import com.chatbot.web.domains.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper {
    public void insertExamData(Exam exam);
    public void updateExamData(Exam exam);
    public void deleteExamData(Exam exam);
    public Exam findOndExamData(Exam exam);
    public List<Exam> findAllExamData();
}