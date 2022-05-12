package ru.krylov.myfirstkrylbot.botapi.handler.profile;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Set;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GettingProfilesHandler implements InputMessageHandler {

    UserDataCache userDataCache;
    ReplyMessageService messageService;
    UserService userService;
    TelegramBot bot;

    public GettingProfilesHandler(UserDataCache userDataCache, ReplyMessageService messageService,
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
        return BotState.GETTING_PROFILES;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        SendMessage replyToUser;

        Set<User> users = userService.getAllUsersWithFilledProfile();
        int i = 0;
        for (User user : users) {
            String form = String.format("Имя: %s\nТелефон: %s\nПроблема/запрос: %s\n" +
                            "Отправлено: %4$td %4$tB %4$tY года %n%4$tH:%4$tM:%4$tS",
                    user.getFirstName(),
                    user.getPhone(),
                    user.getRequest(),
                    user.getFormCreatedDate());
            bot.sendMessage(String.valueOf(userId), form);
            i++;
        }

        if (i > 0) {
            replyToUser = new SendMessage(String.valueOf(userId), messageService.getReplyText("reply.profiles.received")
                                                                     + " Заполненных анкет: " + i);
        } else {
            replyToUser = new SendMessage(String.valueOf(userId), "Пока ещё нет ни одной оставленной анкеты");
        }
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        return replyToUser;
    }
}
