/*
package com.chatbot.web.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickle.web.users.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name="chat", uniqueConstraints = {@UniqueConstraint(columnNames = {"chat_idx"})})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id") private Long id;

    @Column(name="chat_idx") private int chatIdx;

    @Column(name="chat_kind") private String chatKind;

    @Column(name="chat_body") private String chatBody;

    @CreationTimestamp
    @Column(name="insert_date") private LocalDateTime insertDate;

    @CreationTimestamp
    @Column(name="update_date") private LocalDateTime updateDate;

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private List<ChatHistory> chatHistories;

    @OneToOne
    @JoinColumn(name="user_code") @NotNull
    private User user;
}

 */