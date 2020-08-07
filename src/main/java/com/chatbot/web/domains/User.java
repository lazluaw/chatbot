package com.chatbot.web.domains;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Lazy @Data
@Alias("user")
@MappedTypes(LocalDate.class)
public class User {
    private int id;
    private int userCode, schoolCode, curGrade, homeClass, positionChecker;
    private String name, userId, userPw, email, phone, guardianPhone, guardianRelation, gender;
    private LocalDateTime insertDate, updateDate;
}