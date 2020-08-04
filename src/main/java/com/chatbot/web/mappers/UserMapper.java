package com.chatbot.web.mappers;

import com.chatbot.web.domains.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public String selectAdminId();
    public String selectAdminPw();
    public String selectAdminName();
    public String selectUserId();
    public String selectUserPw();
    public String selectUserName();
}