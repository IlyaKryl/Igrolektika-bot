package ru.krylov.myfirstkrylbot.botapi.handler.mailing;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.InputMessageHandler;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailingHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;

    public MailingHandler(UserDataCache userDataCache, ReplyMessageService messageService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAILING;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        SendMessage replyToUser = messageService.getReplyMessage(String.valueOf(userId), "reply.mailing");
        userDataCache.setUsersCurrentBotState(userId, BotState.MAIL_SENDING);

        return replyToUser;
    }
}
