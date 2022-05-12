package ru.krylov.myfirstkrylbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.krylov.myfirstkrylbot.exception.UserNotFoundException;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByChatId(long id) {
        if (!userRepository.existsByChatId(id)) {
            User user = new User();
            user.setChatId(id);
            userRepository.save(user);
        }
        User user = userRepository.findByChatId(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " was not found"));

        log.info("In findByChatId: user " + user.getFirstName() + " found by id: " + id);
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("In findAllUsers: " + users.size() + " users have been found ");
        return users;
    }

    public Set<User> findAllUsersWithFilledProfile() {
        Set<User> users = new HashSet<>();
        List<User> all = userRepository.findAll();
        for (User user: all) {
            if (user.getFirstName() != null && user.getPhone() != null
                    && user.getRequest() != null && user.getFormCreatedDate() != null) {
                users.add(user);
            }
        }

        return users;
    }
    public Set<User> getAllUsersWithFilledProfile() {
        return userRepository.findUsersByFormCreatedDateIsNotNull();
    }

    public void removeUser(User user) {
        userRepository.delete(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean isExist(long id) {
        return userRepository.existsByChatId(id);
    }
}
