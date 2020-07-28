package com.chatbot.web.mappers;

import com.chatbot.web.domains.ExamAnalysis;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExamAnalysisMapper {
    public List<ExamAnalysis> selectList();
}