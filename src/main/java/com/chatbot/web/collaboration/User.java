package com.chatbot.web.collaboration;

import lombok.Data;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component @Lazy
@Data
@MappedTypes(LocalDate.class)
public class User {
    private Long id;
    private int userCode, schoolCode, curGrade, homeClass, positionChecker;
    private String name, userId, userPw, email, phone, gardianPhone, gardianRelation, gender;
    private LocalDateTime insertDate, updateDate;
}
