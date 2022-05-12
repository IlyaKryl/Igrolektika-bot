package ru.krylov.myfirstkrylbot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "questions")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    @Id
    @SequenceGenerator(name = "questions_id_seq", sequenceName = "questions_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_id_seq")
    long id;

    @Column(name = "chat_id")
    long chatId;

    @Column(name = "question")
    String question;

    @Column(name = "message_id")
    int messageId;

    @Column(name = "replied")
    boolean replied;

    @CreatedDate
    @Column(name = "created", nullable = false)
    Date created;
}
