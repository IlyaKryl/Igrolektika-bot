package ru.krylov.myfirstkrylbot.botapi.handler.question;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.InputMessageHandler;
import ru.krylov.myfirstkrylbot.model.TelegramBot;
import ru.krylov.myfirstkrylbot.model.Question;
import ru.krylov.myfirstkrylbot.service.MessageService.ReplyMessageService;
import ru.krylov.myfirstkrylbot.service.QuestionService;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReplyQuestionHandler implements InputMessageHandler {
    ReplyMessageService messageService;
    QuestionService questionService;
    TelegramBot bot;

    public ReplyQuestionHandler(ReplyMessageService messageService, QuestionService questionService,
                                @Lazy TelegramBot bot) {
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
        return BotState.REPLY_QUESTION;
    }

    @SneakyThrows
    private SendMessage processUsersInput(Message inputMessage) {
        long userId = inputMessage.getFrom().getId();

        SendMessage replyToAdmin;

        String reply = inputMessage.getText();

        if (!inputMessage.isReply()) {
            return new SendMessage(String.valueOf(userId), "Ответьте на вопрос пользователя");
        }

        String question = inputMessage.getReplyToMessage().getText();

        if (questionService.existsQuestion(question)) {
            Question questionInDB = questionService.findQuestion(question);
            SendMessage replyToUser = new SendMessage();
            replyToUser.setChatId(String.valueOf(questionInDB.getChatId()));
            replyToUser.setReplyToMessageId(questionInDB.getMessageId());
            replyToUser.setText(reply);

            bot.execute(replyToUser);

            questionInDB.setReplied(true);
            questionService.save(questionInDB);

            replyToAdmin = messageService.getReplyMessage(String.valueOf(userId), "reply.successful");
        } else {
            replyToAdmin = messageService.getReplyMessage(String.valueOf(userId), "reply.somethingWrong");
        }
        return replyToAdmin;
    }
}
