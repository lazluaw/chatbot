/*
package com.chatbot.web.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name="chathistory", uniqueConstraints = {@UniqueConstraint(columnNames = {"history_idx"})})
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="history_id") private Long id;

    @Column(name="history_idx") private int historyIdx;

    @Column(name="chat_body") private String chatBody;

    @CreationTimestamp
    @Column(name="insert_date") private LocalDateTime insertDate;

    @CreationTimestamp
    @Column(name="update_date") private LocalDateTime updateDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_idx") @NotNull
    private Chat chat;
}

 */