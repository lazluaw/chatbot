package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public Chat selectUserList(int chat);
    public Chat selectList();
    public void updateData(Chat chat);
    public void deleteData(Chat chat);
    public ArrayList<Chat> selectAverage(int chat);
}