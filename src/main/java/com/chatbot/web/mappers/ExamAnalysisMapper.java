package com.chatbot.web.mappers;

import com.chatbot.web.domains.ExamAnalysis;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExamAnalysisMapper {
    public void insertAnalysisData(ExamAnalysis examAnalysis);
    public void updateAnalysisData(ExamAnalysis examAnalysis);
    public void deleteAnalysisData(ExamAnalysis examAnalysis);
    public ExamAnalysis findOneAnalysisData(ExamAnalysis examAnalysis);
    public List<ExamAnalysis> findAllAnalysisData();
}