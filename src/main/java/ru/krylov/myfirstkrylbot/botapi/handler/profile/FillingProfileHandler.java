package ru.krylov.myfirstkrylbot.botapi.handler.profile;

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
import ru.krylov.myfirstkrylbot.botapi.keyboard.Keyboard;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.model.TelegramBot;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.UserService;

import java.util.Date;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FillingProfileHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;
    TelegramBot bot;
    Keyboard keyboard;
    UserService userService;
    @Value("${telegrambot.adminIdIlya}")
    String adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    String adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    String adminIdDarya;

    public FillingProfileHandler(UserDataCache userDataCache, ReplyMessageService messageService,
                                 @Lazy TelegramBot bot, Keyboard keyboard, UserService userService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.bot = bot;
        this.keyboard = keyboard;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME);
        }

        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMessage) {

        String userAnswer = inputMessage.getText();
        long userId = inputMessage.getFrom().getId();
        long chatId = inputMessage.getChatId();

        UserProfileData profileData = userDataCache.getUsersProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        User user = userService.findByChatId(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_NAME)) {
            profileData.setName(userAnswer);
            System.out.println(userAnswer);
            user.setFirstName(profileData.getName());
            replyToUser = messageService.getReplyMessage(String.valueOf(chatId), "reply.askPhoneNumber");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_PHONE_NUMBER);
            userService.save(user);
            System.out.println(profileData.getName());
        }

        if (botState.equals(BotState.ASK_PHONE_NUMBER)) {
            if (!userAnswer.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) {
                replyToUser = new SendMessage(String.valueOf(chatId), "Вы ввели неверный номер телефона, попробуйте снова");
            } else {
                user.setPhone(userAnswer);
                profileData.setPhoneNumber(userAnswer);
                replyToUser = messageService.getReplyMessage(String.valueOf(chatId), "reply.askProblem");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_PROBLEM);
                userService.save(user);
                System.out.println(profileData.getPhoneNumber());
            }
        }

        if (botState.equals(BotState.ASK_PROBLEM)) {
            user.setRequest(userAnswer);
            profileData.setRequest(userAnswer);
            Date date = new Date();
            user.setFormCreatedDate(date);
            profileData.setFormCreatedDate(date);
            replyToUser = messageService.getReplyMessage(String.valueOf(chatId), "reply.profileFilled");
            replyToUser.setReplyMarkup(keyboard.getInlineKeyboardContinue());
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
            userService.save(user);
            String form = String.format("Имя: %s\nТелефон: %s\nПроблема/запрос: %s\n" +
                            "%4$td %4$tB %4$tY года %n%4$tH:%4$tM:%4$tS",
                    profileData.getName() != null ?
                            profileData.getName() : userService.findByChatId(userId).getFirstName(),
                    profileData.getPhoneNumber() != null ?
                            profileData.getPhoneNumber() : userService.findByChatId(userId).getPhone(),
                    profileData.getRequest() != null ?
                            profileData.getRequest() : userService.findByChatId(userId).getRequest(),
                    profileData.getFormCreatedDate() != null ?
                            profileData.getFormCreatedDate() : userService.findByChatId(userId).getFormCreatedDate());
            bot.sendMessage(String.valueOf(chatId), form);
            String userFormCreated = "⚡ <strong>Новая анкета</strong> ⚡\n\n";
            bot.sendMessage(adminIdIlya, userFormCreated + form);
            bot.sendMessage(adminIdAnya, userFormCreated + form);
            bot.sendMessage(adminIdDarya, userFormCreated + form);
            log.info("Current profile: Name {}, Phone number: {}, Question: {}",
                    profileData.getName(), profileData.getPhoneNumber(), profileData.getRequest());
        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }
}
