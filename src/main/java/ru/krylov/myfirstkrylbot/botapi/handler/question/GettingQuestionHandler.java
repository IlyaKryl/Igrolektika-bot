package ru.krylov.myfirstkrylbot.botapi.handler.question;

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
import ru.krylov.myfirstkrylbot.model.Question;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.QuestionService;

import java.util.Set;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GettingQuestionHandler implements InputMessageHandler {
    UserDataCache userDataCache;
    ReplyMessageService messageService;
    QuestionService questionService;
    TelegramBot bot;

    public GettingQuestionHandler(UserDataCache userDataCache, ReplyMessageService messageService,
                                  QuestionService questionService, @Lazy TelegramBot bot) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.questionService = questionService;
        this.bot = bot;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GETTING_QUESTIONS;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        Set<Question> questions = questionService.getAllNotRepliedQuestions();

        SendMessage replyToUser;

        int i = 0;
        for (Question question: questions) {
            bot.sendMessage(String.valueOf(userId), question.getQuestion());
            i++;
        }

        if (i > 0) {
            replyToUser = new SendMessage(String.valueOf(userId), messageService.getReplyText("reply.questions.received") +
                                                                         " Новых вопросов: " + i);
        } else {
            replyToUser = new SendMessage(String.valueOf(userId), "Нет новых вопросов");
        }

        userDataCache.setUsersCurrentBotState(userId, BotState.REPLY_QUESTION);

        return replyToUser;
    }
}
