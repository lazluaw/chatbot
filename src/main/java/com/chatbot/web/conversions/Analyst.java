package com.chatbot.web.conversions;

import com.chatbot.web.domains.Chat;
import com.chatbot.web.domains.ChatHistory;
import com.chatbot.web.domains.Exam;
import com.chatbot.web.mappers.ChatHistoryMapper;
import com.chatbot.web.mappers.ChatMapper;
import com.chatbot.web.mappers.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Analyst {
    @Autowired Chat chat;
    @Autowired ChatHistory chatHistory;
    @Autowired Exam exam;
    @Autowired ChatMapper chatMapper;
    @Autowired ChatHistoryMapper chatHistoryMapper;
    @Autowired ExamMapper examMapper;
    public void examAnalysis() {
        System.out.println(chatMapper.findOneChat(chat.getId()));
    }
}
