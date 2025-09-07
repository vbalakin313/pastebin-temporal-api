package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balakinvv.pastebintemporalapi.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
