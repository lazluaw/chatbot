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
    private double fs, ses, ts, fos, fis, sis, sevs, es, ns, fms, ffs, sms, sfs, avgScore, avgHome, fs1, ses1, ts1, fos1, fis1, sis1, sevs1, es1, ns1, fms1, ffs1, sms1, sfs1;
    private String subjectCode, codeSubjectKor, codeExamKor, wrongAnswer, subjectMemo, homeMemo;
    private LocalDateTime insertDate;
}