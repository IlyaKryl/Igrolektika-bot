package ru.krylov.myfirstkrylbot.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.krylov.myfirstkrylbot.botapi.TelegramFacade;
import ru.krylov.myfirstkrylbot.model.TelegramBot;

@Configuration
public class AppConfig {
    private final BotConfig botConfig;

    public AppConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        TelegramBot bot = new TelegramBot(telegramFacade, setWebhook);
        bot.setBotToken(botConfig.getBotToken());
        bot.setBotUserName(botConfig.getBotUserName());
        bot.setWebHookPath(botConfig.getWebHookPath());

        return bot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
