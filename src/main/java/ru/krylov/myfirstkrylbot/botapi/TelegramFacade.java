package ru.krylov.myfirstkrylbot.botapi;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.krylov.myfirstkrylbot.botapi.handler.callbackquery.CallbackQueryHandler;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class TelegramFacade {
    UserDataCache userDataCache;
    BotStateContext botStateContext;
    CallbackQueryHandler callbackQueryHandler;
    @Value("${telegrambot.adminIdIlya}")
    Long adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    Long adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    Long adminIdDarya;

    public TelegramFacade(UserDataCache userDataCache, BotStateContext botStateContext,
                          CallbackQueryHandler callbackQueryHandler) {
        this.userDataCache = userDataCache;
        this.botStateContext = botStateContext;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    callbackQuery.getFrom().getUserName(),
                    callbackQuery.getFrom().getId(),
                    callbackQuery.getData());
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        }

        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User: {}, chatId: {}, with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            log.info(String.valueOf(userDataCache.getUsersCurrentBotState(message.getChatId())));
            replyMessage = handleInputMessage(message);
        }

       return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText();
        long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMessage) {
            case "/start":
                botState = BotState.GREETING;
                break;
            case "Главное меню":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Вопросы пользователей":
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    botState = BotState.GETTING_QUESTIONS;
                    break;
                }
            case "Заполненные анкеты" :
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    botState = BotState.GETTING_PROFILES;
                    break;
                }
            case "Сделать рассылку":
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    botState = BotState.MAILING;
                    break;
                }
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

            userDataCache.setUsersCurrentBotState(userId, botState);

            replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
