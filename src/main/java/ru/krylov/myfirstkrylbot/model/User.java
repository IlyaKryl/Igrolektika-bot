package ru.krylov.myfirstkrylbot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import ru.krylov.myfirstkrylbot.botapi.BotState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    long id;

    @Column(name = "chat_id", nullable = false)
    long chatId;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "phone")
    String phone;

    @Column(name = "request")
    String request;

    @Column(name = "form_created_date")
    Date formCreatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "bot_state")
    BotState botState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    List<Question> questions;
}
