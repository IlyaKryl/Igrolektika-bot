package ru.krylov.myfirstkrylbot.cache;

import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.handler.profile.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    UserProfileData getUsersProfileData(long userId);

    void saveUserProfileData(long userId, UserProfileData userProfileData);
}
