package ru.krylov.myfirstkrylbot.botapi.handler.mailing;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.InputMessageHandler;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.model.TelegramBot;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.UserService;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailSendingHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;
    UserService userService;
    TelegramBot bot;
    @Value("${telegrambot.adminIdIlya}")
    Long adminIlyaId;
    @Value("${telegrambot.adminIdAnya}")
    Long adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    Long adminIdDarya;

    public MailSendingHandler(UserDataCache userDataCache, ReplyMessageService messageService,
                                  UserService userService, @Lazy TelegramBot bot) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.userService = userService;
        this.bot = bot;
    }


    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIL_SENDING;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();
        String adminMessageToMailing = inputMessage.getText();

        SendMessage replyToUser = messageService.getReplyMessage(String.valueOf(userId), "reply.mailSending");

        List<User> users = userService.findAllUsers();
        for (User user: users) {
            if (user.getChatId() != adminIlyaId && user.getChatId() != adminIdAnya && user.getChatId() != adminIdDarya) {
                bot.sendMessage(String.valueOf(user.getChatId()), adminMessageToMailing);
            }
        }
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        return replyToUser;
    }
}
