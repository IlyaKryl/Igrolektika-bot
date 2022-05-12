package ru.krylov.myfirstkrylbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.krylov.myfirstkrylbot.model.TelegramBot;

@RestController
public class WebHookController {
    private final TelegramBot telegramBot;

    public WebHookController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().build();
    }
}
