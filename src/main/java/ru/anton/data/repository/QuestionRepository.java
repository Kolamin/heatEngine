package ru.anton.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anton.data.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(long id);
}
