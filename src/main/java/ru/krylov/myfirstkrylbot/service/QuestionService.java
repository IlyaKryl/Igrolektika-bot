package ru.krylov.myfirstkrylbot.service;

import org.springframework.stereotype.Service;
import ru.krylov.myfirstkrylbot.exception.UserNotFoundException;
import ru.krylov.myfirstkrylbot.model.Question;
import ru.krylov.myfirstkrylbot.model.User;
import ru.krylov.myfirstkrylbot.repository.QuestionRepository;
import ru.krylov.myfirstkrylbot.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public QuestionService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public List<Question> getByUserChatId(long id) {
        User user = userRepository.findByChatId(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + "was not found"));;
        return user.getQuestions();
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Set<Question> getAllNotRepliedQuestions() {
        return questionRepository.findQuestionsByRepliedIsFalse();
    }

    public Question findQuestion(String question) {
        return questionRepository.findQuestionByQuestion(question);
    }

    public boolean existsQuestion(String question) {
        return questionRepository.existsQuestionByQuestion(question);
    }

    public Question getByQuestionId(long id) {
        return questionRepository.findById(id);
    }

    public void removeQuestion(Question question) {
        questionRepository.delete(question);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }
}
