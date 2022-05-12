package ru.krylov.myfirstkrylbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig {
    @Value("${telegrambot.webHookPath}")
    String webHookPath;
    @Value("${telegrambot.userName}")
    String botUserName;
    @Value("${telegrambot.botToken}")
    String botToken;
}
