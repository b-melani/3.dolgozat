package com.szakaszzaro.dolgozat.controller;

import com.szakaszzaro.dolgozat.configuration.PagingProperties;
import com.szakaszzaro.dolgozat.entity.SuperHero;
import com.szakaszzaro.dolgozat.entity.Team;
import com.szakaszzaro.dolgozat.exception.ValidationException;
import com.szakaszzaro.dolgozat.service.SuperHeroService;
import com.szakaszzaro.dolgozat.service.TeamService;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/super-hero")
public class SuperHeroController {

    private final SuperHeroService superHeroService;

    public SuperHeroController(SuperHeroService superHeroService, PagingProperties pagingProperties) {
        this.superHeroService = superHeroService;
        this.pagingProperties = pagingProperties;
    }

    private final PagingProperties pagingProperties;
    private static final Logger log = LoggerFactory.getLogger(SuperHeroController.class);


    @PostMapping("/add")
    public SuperHero addSuperHero(@RequestBody SuperHero superHero) {
        log.info("Received addSuperHero request {} ...", superHero);
        try {
            SuperHero result = superHeroService.createSuperHero(superHero);
            log.debug("Result is: {}.", result);
            return result;
        } catch (ValidationException e) {
            e.printStackTrace();
            log.error("Error when creating superHero");
            return null;
        }
    }

    @PutMapping("/updatesuperhero/{id}")
    public SuperHero updateSuperHero(@PathVariable("id") String superHeroId, @RequestBody SuperHero superHero) {
        log.info("Received Team update request for id {} with updates: {}", superHeroId, superHero);
        try {
            SuperHero updatedHero = superHeroService.updateTeam(superHeroId, superHero);
            log.debug("The updated SH is: {}", updatedHero);
            return updatedHero;
        } catch (ValidationException e) {
            log.error("Superhero with given id ({}) not found.", superHeroId);
            return null;
        }
    }


    @GetMapping("/allfoods")
    public List<SuperHero> getSuperHeros(@RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "limit", required = false) Optional<Integer> limit) {

        log.info("Retrieving SH-s (page: {}, limit: {}) ...", page.isPresent() ? page.get() : "n.a.", limit.orElse(pagingProperties.getDefaultLimit()));
        List<SuperHero> heroList;
        if (page.isPresent()) {
            heroList = superHeroService.listHeros(
                    PageRequest.of(page.get(), limit.orElse(pagingProperties.getDefaultLimit())));
        } else {
            heroList = superHeroService.listHeros();
        }
        log.debug("Found teams: {}", heroList.size());
        return heroList;
    }

    @GetMapping("/getTheFood")
    public SuperHero getSHById(@RequestParam(value = "id") String id) {
        log.info("Received a request for superhero with id: {}", id);
        try {
            SuperHero superHero = superHeroService.giveTheHero(id);
            return superHero;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Hero with id: {} not found.", id);
        }
        return null;
    }

}
