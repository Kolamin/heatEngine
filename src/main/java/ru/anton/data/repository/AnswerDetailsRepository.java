package ru.anton.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anton.data.entity.AnswerDetails;

public interface AnswerDetailsRepository  extends JpaRepository<AnswerDetails, Long> {
}
