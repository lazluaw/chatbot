package com.chatbot.web.mappers;

import com.chatbot.web.domains.ExamAnalysis;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamAnalysisMapper {
    public ExamAnalysis selectList(int examAnalysis);
}