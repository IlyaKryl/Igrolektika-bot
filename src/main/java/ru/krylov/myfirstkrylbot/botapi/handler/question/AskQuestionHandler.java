package ru.krylov.myfirstkrylbot.botapi.handler.question;

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
import ru.krylov.myfirstkrylbot.model.Question;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.QuestionService;
import ru.krylov.myfirstkrylbot.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AskQuestionHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;
    Keyboard keyboard;
    UserService userService;
    QuestionService questionService;
    TelegramBot bot;
    @Value("${telegrambot.adminIdIlya}")
    String adminIdIlya;
    @Value("${telegrambot.adminIdAnya}")
    String adminIdAnya;
    @Value("${telegrambot.adminIdDarya}")
    String adminIdDarya;

    public AskQuestionHandler(UserDataCache userDataCache, ReplyMessageService messageService, Keyboard keyboard,
                              UserService userService, QuestionService questionService, @Lazy TelegramBot bot) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.keyboard = keyboard;
        this.userService = userService;
        this.questionService = questionService;
        this.bot = bot;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_QUESTION;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        User user = userService.findByChatId(userId);

        SendMessage replyToUser;

        replyToUser = messageService.getReplyMessage(String.valueOf(userId), "reply.afterQuestion");
        replyToUser.setReplyMarkup(keyboard.getInlineKeyboardContinue());

        Question question = new Question();
        question.setChatId(userId);
        question.setQuestion(inputMessage.getText());
        question.setMessageId(inputMessage.getMessageId());
        question.setReplied(false);
        question.setCreated(new Date());

        List<Question> userQuestionsList;
        if (user.getQuestions() != null) {
            userQuestionsList = user.getQuestions();
        } else {
            userQuestionsList = new ArrayList<>();
        }
        userQuestionsList.add(question);
        user.setQuestions(userQuestionsList);

        questionService.save(question);
        userService.save(user);

        String userQuestion = "⚡ <strong>Получен новый вопрос</strong> ⚡";
        bot.sendMessage(adminIdIlya, userQuestion);
        bot.sendMessage(adminIdAnya, userQuestion);
        bot.sendMessage(adminIdDarya, userQuestion);

        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        log.info(replyToUser.toString());

        return replyToUser;
    }
}
