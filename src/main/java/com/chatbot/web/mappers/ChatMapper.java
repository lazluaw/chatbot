package com.chatbot.web.mappers;

import com.chatbot.web.domains.Chat;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface ChatMapper {
    public void insertData(Chat chat);
    public Date selectIDate();
    public Date selectUDate();
    public void updateData(Chat chat);
    public void deleteData(Chat chat);
}