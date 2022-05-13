package ru.krylov.myfirstkrylbot.botapi.handler.callbackquery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.keyboard.AdminKeyboard;
import ru.krylov.myfirstkrylbot.botapi.keyboard.Keyboard;
import ru.krylov.myfirstkrylbot.cache.UserDataCache;
import ru.krylov.myfirstkrylbot.model.TelegramBot;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;

@Component
public class CallbackQueryHandler {
    UserDataCache userDataCache;
    ReplyMessageService replyMessageService;
    Keyboard keyboard;
    AdminKeyboard adminKeyboard;
    TelegramBot bot;
    @Value("${telegrambot.adminIdIlya}")
    Long adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    Long adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    Long adminIdDarya;

    public CallbackQueryHandler(UserDataCache userDataCache, ReplyMessageService messageService, Keyboard keyboard,
                                AdminKeyboard adminKeyboard, @Lazy TelegramBot bot) {
        this.userDataCache = userDataCache;
        this.replyMessageService = messageService;
        this.keyboard = keyboard;
        this.adminKeyboard = adminKeyboard;
        this.bot = bot;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        long userId = buttonQuery.getFrom().getId();

        SendMessage callBackAnswer;

        switch (buttonQuery.getData()) {
            case "buttonToOtherServices":
            case "buttonServices":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.aboutServices");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardServices());
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonAboutProject":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.aboutProject");
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardInAboutProjectAdmin());
                } else {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardInAboutProject());
                }
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonAboutAuthor":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.aboutAuthor");
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardInAboutAuthorAdmin());
                } else {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardInAboutAuthor());
                }
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonFreeMaterials":
//                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.freeMaterials");
                callBackAnswer = new SendMessage(String.valueOf(userId), "");
                bot.sendMessage(String.valueOf(userId), "Отправляем файл, подождите");
                bot.sendDocument(String.valueOf(userId), "", "mindExercises_7games.pdf");
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonSocialMedia":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.socialMedia");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardMarkupURL());
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonAdminMode":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.aboutAdminMode");
                callBackAnswer.setReplyMarkup(adminKeyboard.getAdminKeyboard());
                break;
            case "buttonAskQuestion":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.askQuestion");
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_QUESTION);
                break;
            case "buttonMindFitness":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Mind fitness</strong>\nСтоимость занятия: 4 000 рублей", "mindFitness.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.mindFitness");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonBookClub":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Книжный клуб</strong>\nСтоимость занятия: 500 рублей", "bookClub.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.bookClub");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonFreeConsultation":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Консультация</strong>\nСтоимость занятия: бесплатно", "consultation.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.freeConsultation");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonPreparationForSchool":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Подготовка к школе</strong>\nСтоимость занятия: 1 000 рублей", "preparationForSchool.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.preparationForSchool");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonSoftSkills":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Soft skills</strong>\nСтоимость занятия: 2 000 рублей", "softSkills.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.softSkills");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonMentalArithmetic":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Ментальная арифметика</strong>\nСтоимость занятия: 1 500 рублей", "mentalArithmetic.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.mentalArithmetic");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonSpeedReading":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Скорочтение</strong>\nСтоимость занятия: 1 500 рублей", "speedReading.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.speedReading");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonSuperMemory":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Супер память</strong>\nСтоимость занятия: 2 000 рублей", "superMemory.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.superMemory");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonCreativeThinking":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Креативное мышление</strong>\nСтоимость занятия: 2 000 рублей", "creativeThinking.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.creativeThinking");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonConcentrationOfAttention":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Концентрация внимания</strong>\nСтоимость занятия: 2 000 рублей", "concentrationOfAttention.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.concentrationOfAttention");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonHowToRealizeYourIdea":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>От идеи до проекта: как реализовать свою задумку</strong>\nСтоимость занятия: 2 000 рублей", "howToRealizeYourIdea.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.howToRealizeYourIdea");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonBusinessGame":
                bot.sendImage(String.valueOf(userId), "\uD83D\uDCA1 <strong>Деловая игра</strong>\nСтоимость занятия: 500 - 1 500 рублей", "businessGame.jpg");
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.services.businessGame");
                callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardIntoServices());
                break;
            case "buttonContinue":
            case "buttonThinkMore":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.thinkMore");
                if (userId == adminIdIlya || userId == adminIdAnya || userId == adminIdDarya) {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardMainAdmin());
                } else {
                    callBackAnswer.setReplyMarkup(keyboard.getInlineKeyboardMain());
                }
                userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
                break;
            case "buttonEnroll":
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.enroll");
                userDataCache.setUsersCurrentBotState(userId, BotState.FILLING_PROFILE);
                break;
            default:
                callBackAnswer = replyMessageService.getReplyMessage(String.valueOf(userId), "reply.badWay");
                break;
        }

        return callBackAnswer;
    }
}
