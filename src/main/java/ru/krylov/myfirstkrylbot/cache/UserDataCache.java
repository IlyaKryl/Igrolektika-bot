package ru.krylov.myfirstkrylbot.cache;

import org.springframework.stereotype.Component;
import ru.krylov.myfirstkrylbot.botapi.BotState;
import ru.krylov.myfirstkrylbot.botapi.handler.profile.UserProfileData;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private final UserService userService;
    Map<Long, BotState> usersBotStates = new HashMap<>();
    Map<Long, UserProfileData> usersProfileData = new HashMap<>();

    public UserDataCache(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId, botState);
        if (userService.isExist(userId)) {
            User user = userService.findByChatId(userId);
            user.setBotState(botState);
            userService.save(user);
        }
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            if (userService.isExist(userId)) {
                botState = userService.findByChatId(userId).getBotState();
            } else {
                botState = BotState.SHOW_MAIN_MENU;
            }
        }
        return botState;
    }

    @Override
    public UserProfileData getUsersProfileData(long userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }

        return userProfileData;
    }

    @Override
    public void saveUserProfileData(long userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
}
