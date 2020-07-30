package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.Exam;
import com.chatbot.web.domains.ExamAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Analyst {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired Exam exam;
    @Autowired ExamAnalysis examAnalysis;
    public void examAnalysis() {
        System.out.println("---chat---");
        System.out.println("id: "+chat.getId());
        System.out.println("chatBody: "+chat.getChatBody());

        System.out.println("---chat history---");
        System.out.println(chatHistory.getChatId());
        System.out.println("chatKind: "+chatHistory.getChatKind());
        System.out.println(chatHistory.getChatBody());

        System.out.println("---exam---");
        System.out.println(exam.getExamKind());
        System.out.println(exam.getExamAnswer());
        System.out.println(exam.getExamChoice1());
        System.out.println(exam.getExamChoice2());
        System.out.println(exam.getExamChoice3());
        System.out.println(exam.getExamChoice4());
        System.out.println(exam.getExamChoice5());
        System.out.println(exam.getExamContent());
        System.out.println(exam.getExamDivision());
        System.out.println(exam.getExamNum());
        System.out.println(exam.getExamQuestion());
        System.out.println(exam.getSubejectCode());

        System.out.println("---exam_analysis---");
        System.out.println(examAnalysis.getExamKind());
        System.out.println(examAnalysis.getExamNum());
        System.out.println(examAnalysis.getSubjectCode());
        System.out.println(examAnalysis.getWrongAnswer());
    }
}
