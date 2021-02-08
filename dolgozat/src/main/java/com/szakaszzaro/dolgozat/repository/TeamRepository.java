package com.szakaszzaro.dolgozat.repository;

import com.szakaszzaro.dolgozat.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
}
