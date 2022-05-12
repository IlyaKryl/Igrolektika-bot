package ru.krylov.myfirstkrylbot.botapi.handler.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.InputMessageHandler;
import ru.krylov.myfirstkrylbot.botapi.keyboard.AdminKeyboard;
import ru.krylov.myfirstkrylbot.botapi.keyboard.Keyboard;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.UserService;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GreetingHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;
    Keyboard keyboard;
    UserService userService;
    @Value("${telegrambot.adminIdIlya}")
    Long adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    Long adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    Long adminIdDarya;

    public GreetingHandler(UserDataCache userDataCache, ReplyMessageService messageService, Keyboard keyboard,
                           UserService userService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.keyboard = keyboard;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GREETING;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        SendMessage replyToUser;

            replyToUser = messageService.getReplyMessage(String.valueOf(userId), "reply.greeting");

            if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                replyToUser.setReplyMarkup(keyboard.getInlineKeyboardMainAdmin());
            } else {
                replyToUser.setReplyMarkup(keyboard.getInlineKeyboardMain());
            }

            if (!userService.isExist(userId)) {
                User user = new User();
                user.setChatId(userId);
                userService.save(user);
            }

            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

            log.info(replyToUser.toString());

        return replyToUser;
    }
}
