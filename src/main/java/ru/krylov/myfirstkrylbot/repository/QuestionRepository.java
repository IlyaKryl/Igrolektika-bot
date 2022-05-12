package ru.krylov.myfirstkrylbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krylov.myfirstkrylbot.model.Question;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(long id);
    Set<Question> findQuestionsByRepliedIsFalse();
    Question findQuestionByQuestion(String question);
    boolean existsQuestionByQuestion(String question);
}
