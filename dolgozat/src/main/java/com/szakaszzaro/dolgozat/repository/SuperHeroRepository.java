package com.szakaszzaro.dolgozat.repository;

import com.szakaszzaro.dolgozat.entity.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperHeroRepository extends JpaRepository<SuperHero, String> {

}
