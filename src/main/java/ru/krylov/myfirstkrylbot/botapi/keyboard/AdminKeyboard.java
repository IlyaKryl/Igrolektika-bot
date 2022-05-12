package ru.krylov.myfirstkrylbot.botapi.keyboard;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminKeyboard {
    final KeyboardButton allUnansweredQuestions = new KeyboardButton("Вопросы пользователей");
    final KeyboardButton allUsers = new KeyboardButton("Заполненные анкеты");
    final KeyboardButton toMainMenu = new KeyboardButton("Главное меню");
    final KeyboardButton mailing = new KeyboardButton("Сделать рассылку");

    public SendMessage getAdminMenuMessage(long chatId, String textMessage) {
        ReplyKeyboardMarkup keyboardMarkup = getAdminKeyboard();
        return createMessageWithAdminKeyboard(chatId, textMessage, keyboardMarkup);
    }

    public SendMessage createMessageWithAdminKeyboard(SendMessage sendMessage) {
        sendMessage.setReplyMarkup(getAdminKeyboard());
        return sendMessage;
    }

    public ReplyKeyboardMarkup getAdminKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(allUnansweredQuestions);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(allUsers);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(mailing);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(toMainMenu);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithAdminKeyboard(long chatId, String textMessage,
                                                       ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (keyboardMarkup != null) {
            sendMessage.setReplyMarkup(keyboardMarkup);
        }

        return sendMessage;
    }
}
