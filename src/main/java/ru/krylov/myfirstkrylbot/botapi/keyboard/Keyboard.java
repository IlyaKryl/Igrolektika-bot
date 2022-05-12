package ru.krylov.myfirstkrylbot.botapi.keyboard;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Keyboard {
    @Value("${vk.url}")
    String vkURL;
    @Value("${instagram.url}")
    String instagramURL;
    @Value("${ok.url}")
    String okURL;
    @Value("${yandexDzen.url}")
    String dzenURL;
    final InlineKeyboardButton buttonServices = new InlineKeyboardButton("Услуги");
    final InlineKeyboardButton buttonFreeMaterials = new InlineKeyboardButton("Бесплатные материалы");
    final InlineKeyboardButton buttonAboutProject = new InlineKeyboardButton("О проекте");
    final InlineKeyboardButton buttonAboutAuthor = new InlineKeyboardButton("Об авторе");
    final InlineKeyboardButton buttonSocialMedia = new InlineKeyboardButton("Сайт и социальные сети");
    final InlineKeyboardButton buttonAskQuestion = new InlineKeyboardButton("Задать вопрос");
    final InlineKeyboardButton buttonAdminMode = new InlineKeyboardButton("Админ");

    final InlineKeyboardButton buttonVkGroupUrl = new InlineKeyboardButton("VK");
    final InlineKeyboardButton buttonInstagramUrl = new InlineKeyboardButton("Instagram");
    final InlineKeyboardButton buttonOkUrl = new InlineKeyboardButton("Одноклассники");
    final InlineKeyboardButton buttonYandexDzenUrl = new InlineKeyboardButton("Яндекс.Дзен");

    final InlineKeyboardButton buttonFreeConsultation = new InlineKeyboardButton("Бесплатная консультация");
    final InlineKeyboardButton buttonMindFitness = new InlineKeyboardButton("Mind fitness");
    final InlineKeyboardButton buttonPreparationForSchool = new InlineKeyboardButton("Подготовка к школе");
    final InlineKeyboardButton buttonSoftSkills = new InlineKeyboardButton("Soft skills");
    final InlineKeyboardButton buttonMentalArithmetic = new InlineKeyboardButton("Ментальная арифметика");
    final InlineKeyboardButton buttonSpeedReading = new InlineKeyboardButton("Скорочтение");
    final InlineKeyboardButton buttonSuperMemory = new InlineKeyboardButton("Супер память");
    final InlineKeyboardButton buttonCreativeThinking = new InlineKeyboardButton("Креативное мышление");
    final InlineKeyboardButton buttonConcentrationOfAttention = new InlineKeyboardButton("Концентрация внимания");
    final InlineKeyboardButton buttonHowToRealizeYourIdea =
            new InlineKeyboardButton("От идеи до проекта: как реализовать свою задумку");
    final InlineKeyboardButton buttonBusinessGame = new InlineKeyboardButton("Деловая игра");
    final InlineKeyboardButton buttonBookClub = new InlineKeyboardButton("Книжный клуб");

    final InlineKeyboardButton buttonEnroll = new InlineKeyboardButton("Записаться");
    final InlineKeyboardButton buttonThinkMore = new InlineKeyboardButton("Подумаю ещё");
    final InlineKeyboardButton buttonToOtherServices = new InlineKeyboardButton("Другие услуги");

    final InlineKeyboardButton buttonContinue = new InlineKeyboardButton("Продолжить");

    final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

    {
        buttonServices.setCallbackData("buttonServices");
        buttonFreeMaterials.setCallbackData("buttonFreeMaterials");
        buttonAboutProject.setCallbackData("buttonAboutProject");
        buttonAboutAuthor.setCallbackData("buttonAboutAuthor");
        buttonSocialMedia.setCallbackData("buttonSocialMedia");
        buttonAskQuestion.setCallbackData("buttonAskQuestion");
        buttonAdminMode.setCallbackData("buttonAdminMode");

        buttonFreeConsultation.setCallbackData("buttonFreeConsultation");
        buttonMindFitness.setCallbackData("buttonMindFitness");
        buttonPreparationForSchool.setCallbackData("buttonPreparationForSchool");
        buttonSoftSkills.setCallbackData("buttonSoftSkills");
        buttonMentalArithmetic.setCallbackData("buttonMentalArithmetic");
        buttonSpeedReading.setCallbackData("buttonSpeedReading");
        buttonSuperMemory.setCallbackData("buttonSuperMemory");
        buttonCreativeThinking.setCallbackData("buttonCreativeThinking");
        buttonConcentrationOfAttention.setCallbackData("buttonConcentrationOfAttention");
        buttonHowToRealizeYourIdea.setCallbackData("buttonHowToRealizeYourIdea");
        buttonBusinessGame.setCallbackData("buttonBusinessGame");
        buttonBookClub.setCallbackData("buttonBookClub");

        buttonEnroll.setCallbackData("buttonEnroll");
        buttonThinkMore.setCallbackData("buttonThinkMore");
        buttonToOtherServices.setCallbackData("buttonToOtherServices");

        buttonVkGroupUrl.setCallbackData("buttonVkGroupUrl");
        buttonInstagramUrl.setCallbackData("buttonInstagramUrl");
        buttonOkUrl.setCallbackData("buttonOkUrl");
        buttonYandexDzenUrl.setCallbackData("buttonYandexDzenUrl");

        buttonContinue.setCallbackData("buttonContinue");
    }

    private List<InlineKeyboardButton> createInlineKeyboardButtonRow(InlineKeyboardButton... keyboardButtons) {
        return new ArrayList<>(Arrays.asList(keyboardButtons));
    }

    public InlineKeyboardMarkup getInlineKeyboardMain() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutProject, buttonAboutAuthor));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardMainAdmin() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutProject, buttonAboutAuthor));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));
        rowList.add(createInlineKeyboardButtonRow(buttonAdminMode));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardInAboutProject() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutAuthor));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardInAboutProjectAdmin() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutAuthor));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));
        rowList.add(createInlineKeyboardButtonRow(buttonAdminMode));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardInAboutAuthor() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutProject));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardInAboutAuthorAdmin() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonServices));
        rowList.add(createInlineKeyboardButtonRow(buttonFreeMaterials));
        rowList.add(createInlineKeyboardButtonRow(buttonAboutProject));
        rowList.add(createInlineKeyboardButtonRow(buttonSocialMedia));
        rowList.add(createInlineKeyboardButtonRow(buttonAskQuestion));
        rowList.add(createInlineKeyboardButtonRow(buttonAdminMode));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkupURL() {
        buttonVkGroupUrl.setUrl(vkURL);
        buttonInstagramUrl.setUrl(instagramURL);
        buttonOkUrl.setUrl(okURL);
        buttonYandexDzenUrl.setUrl(dzenURL);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonVkGroupUrl));
        rowList.add(createInlineKeyboardButtonRow(buttonInstagramUrl));
        rowList.add(createInlineKeyboardButtonRow(buttonOkUrl));
        rowList.add(createInlineKeyboardButtonRow(buttonYandexDzenUrl));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardServices() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonFreeConsultation));
        rowList.add(createInlineKeyboardButtonRow(buttonBookClub, buttonSoftSkills));
        rowList.add(createInlineKeyboardButtonRow(buttonMindFitness, buttonPreparationForSchool));
        rowList.add(createInlineKeyboardButtonRow(buttonMentalArithmetic, buttonSpeedReading));
        rowList.add(createInlineKeyboardButtonRow(buttonSuperMemory, buttonCreativeThinking));
        rowList.add(createInlineKeyboardButtonRow(buttonConcentrationOfAttention, buttonBusinessGame));
        rowList.add(createInlineKeyboardButtonRow(buttonHowToRealizeYourIdea));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardIntoServices() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonEnroll));
        rowList.add(createInlineKeyboardButtonRow(buttonThinkMore, buttonToOtherServices));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardContinue() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createInlineKeyboardButtonRow(buttonContinue));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
