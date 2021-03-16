package ru.anton.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anton.data.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User getByUsername(String username);
}
