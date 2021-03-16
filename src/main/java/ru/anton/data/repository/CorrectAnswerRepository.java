package ru.anton.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anton.data.entity.CorrectAnswer;

public interface CorrectAnswerRepository extends JpaRepository<CorrectAnswer, Long> {
    CorrectAnswer findById(long id);
}

