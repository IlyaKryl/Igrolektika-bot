package ru.krylov.myfirstkrylbot.botapi.handler.profile;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileData {
    String name;
    String phoneNumber;
    String request;
    Date formCreatedDate;
}
