package ru.krylov.myfirstkrylbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.krylov.myfirstkrylbot.botapi.TelegramFacade;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends SpringWebhookBot {
    String webHookPath;
    String botUserName;
    String botToken;

    private TelegramFacade telegramFacade;

    public TelegramBot(TelegramFacade telegramFacade , SetWebhook setWebhook) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    public TelegramBot(TelegramFacade telegramFacade, DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
        super.setWebhook(setWebhook);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @SneakyThrows
    public void sendDocument(String chatId, String docCaption, String docInputFileName) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setCaption(docCaption);

        InputFile inputFile = new InputFile(ResourceUtils
                .getFile("classpath:\\static\\documents\\" + docInputFileName), docInputFileName);
        sendDocument.setDocument(inputFile);

        execute(sendDocument);
    }

    @SneakyThrows
    public void sendImage(String chatId, String imgCaption, String imgFileName) {
        SendPhoto sendPhoto = new SendPhoto();
        InputFile inputFile = new InputFile(ResourceUtils
                .getFile("classpath:\\static\\images\\" + imgFileName), imgFileName);
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(imgCaption);
        sendPhoto.setParseMode("HTML");
        sendPhoto.setPhoto(inputFile);

        execute(sendPhoto);
    }

    @SneakyThrows
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.setParseMode("HTML");
        execute(sendMessage);
    }
}
