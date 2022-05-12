package ru.krylov.myfirstkrylbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krylov.myfirstkrylbot.model.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(long chatId);
    Set<User> findUsersByFormCreatedDateIsNotNull();
    Boolean existsByChatId(long chatId);
}
