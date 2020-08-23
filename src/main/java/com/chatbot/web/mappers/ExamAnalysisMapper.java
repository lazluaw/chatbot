package com.chatbot.web.mappers;

import com.chatbot.web.domains.ExamAnalysis;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface ExamAnalysisMapper {
    public ArrayList<ExamAnalysis> selectList(ExamAnalysis examAnalysis);
    public int codeExamKind(String examAnalysis);
    public String codeSubjectKind(String examAnalysis);
    public ArrayList<ExamAnalysis> selectAverage(int examAnalysis);
}