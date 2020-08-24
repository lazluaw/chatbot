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
@Alias("examAnalysis")
@MappedTypes(LocalDate.class)
public class ExamAnalysis {
    private int id, userCode, examKind, codeExam, codeSubject, examNum;
    private double fs, ses, ts, fos, fis, sis, sevs, es, ns, fms, ffs, sms, sfs;
    private String subjectCode, codeSubjectKor, codeExamKor, wrongAnswer;
    private LocalDateTime insertDate;
}