package ru.krylov.myfirstkrylbot.botapi.handler.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.InputMessageHandler;
import ru.krylov.myfirstkrylbot.botapi.keyboard.AdminKeyboard;
import ru.krylov.myfirstkrylbot.botapi.keyboard.Keyboard;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainMenuHandler implements InputMessageHandler {
    ReplyMessageService messageService;
    Keyboard keyboard;
    @Value("${telegrambot.adminIdIlya}")
    Long adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    Long adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    Long adminIdDarya;

    public MainMenuHandler(ReplyMessageService messageService, Keyboard keyboard) {
        this.messageService = messageService;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        SendMessage replyToUser;

            replyToUser = messageService.getReplyMessage(String.valueOf(userId), "reply.thinkMore");

            if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                replyToUser.setReplyMarkup(keyboard.getInlineKeyboardMainAdmin());
            } else {
                replyToUser.setReplyMarkup(keyboard.getInlineKeyboardMain());
            }

            log.info(replyToUser.toString());

        return replyToUser;
    }
}
