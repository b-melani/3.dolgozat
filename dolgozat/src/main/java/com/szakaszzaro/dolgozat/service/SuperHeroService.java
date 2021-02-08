package com.szakaszzaro.dolgozat.service;

import com.szakaszzaro.dolgozat.entity.SuperHero;
import com.szakaszzaro.dolgozat.entity.Team;
import com.szakaszzaro.dolgozat.exception.ValidationException;
import com.szakaszzaro.dolgozat.repository.SuperHeroRepository;
import com.szakaszzaro.dolgozat.repository.TeamRepository;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuperHeroService {
    private final SuperHeroRepository superHeroRepository;

    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    public SuperHero createSuperHero(SuperHero fromRequest) throws ValidationException {
        if (fromRequest.getName() == null || fromRequest.getName() == "") {
            throw new ValidationException("name can not be null or empty string!", HttpStatus.BAD_REQUEST);
        } else {
            log.info("Creating SH based on: {} ...", fromRequest);
            SuperHero result = superHeroRepository.save(fromRequest);
            return result;
        }
    }

    public SuperHero updateSuperHero(String superHeroId, SuperHero superHero) throws ValidationException {
        log.info("Updating team on team id: {}, team: {}", superHeroId, superHero);
        Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(superHeroId);
        if (optionalSuperHero.isEmpty()) {
            throw new ValidationException("There is no such Id", HttpStatus.BAD_REQUEST);
        }
        SuperHero actualHero = optionalSuperHero.get();
        log.debug("Original SH was: {}", actualHero.getName());
        actualHero.setName(superHero.getName());
        actualHero.setUniverse(superHero.getUniverse());
        actualHero.setTeam(superHero.getTeam());
        actualHero.setHero(superHero.getHero());
        SuperHero updated = superHeroRepository.save(actualHero);
        log.debug("Updated SuperHero is: {}", updated);
        return updated;
    }

    public List<SuperHero> listHeros(Pageable pageable) {
        log.info("Listing heros (page information: {}) ...", pageable);

        Page<SuperHero> heroPage = superHeroRepository.findAll(pageable);
        List<SuperHero> heroList = heroPage.getContent();
        log.debug("Total count: {}, total pages: {}", heroPage.getTotalElements(), heroPage.getTotalPages());

        return heroList;
    }

    public List<SuperHero> listHeros() {
        log.info("Listing all heros..");
        List<SuperHero> heroList = superHeroRepository.findAll();
        log.debug("Total count: {}", heroList.size());
        return heroList;
    }

    public SuperHero giveTheHero(String heroId) throws Exception {
        log.info("Received a request on id: {}.", heroId);
        Optional<SuperHero> optionalSuperHero = superHeroRepository.findById(heroId);
        if (optionalSuperHero.isEmpty()) {
            log.error ("There is no hero with id: {}.", heroId);
            throw new ValidationException("There is no hero with sutch id. ");
        }
        SuperHero hero = optionalSuperHero.get();
        log.debug("The hero for id {} is: {}", heroId, hero);
        return hero;
    }
}
