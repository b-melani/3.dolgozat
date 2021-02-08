package com.szakaszzaro.dolgozat.controller;

import com.szakaszzaro.dolgozat.configuration.PagingProperties;
import com.szakaszzaro.dolgozat.entity.Team;
import com.szakaszzaro.dolgozat.exception.ValidationException;
import com.szakaszzaro.dolgozat.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final PagingProperties pagingProperties;
    private static final Logger log = LoggerFactory.getLogger(TeamController.class);


    public TeamController(TeamService teamService, PagingProperties pagingProperties) {
        this.teamService = teamService;
        this.pagingProperties = pagingProperties;
    }

    @PostMapping("/add")
    public Team addTeam(@RequestBody Team team) {
        log.info("Received addTeam request {} ...", team);
        try {
            Team result = teamService.createTeam(team);
            log.debug("Result is: {}.", result);
            return result;
        } catch (ValidationException e) {
            e.printStackTrace();
            log.error("Error when creating team");
            return null;
        }
    }

    @PutMapping("/updateteam/{id}")
    public Team updateTeam(@PathVariable("id") String teamId, @RequestBody Team team) {
        log.info("Received Team update request for id {} with updates: {}", teamId, team);
        try {
            Team updatedTeam = teamService.updateTeam(teamId, team);
            log.debug("The updated food is: {}", updatedTeam);
            return updatedTeam;
        } catch (ValidationException e) {
            log.error("Team with given id ({}) not found.", teamId);
            return null;
        }
    }


    @GetMapping("/allfoods")
    public List<Team> getTeams(@RequestParam(value = "page", required = false) Optional<Integer> page,
                               @RequestParam(value = "limit", required = false) Optional<Integer> limit) {

        log.info("Retrieving teams (page: {}, limit: {}) ...", page.isPresent() ? page.get() : "n.a.", limit.orElse(pagingProperties.getDefaultLimit()));
        List<Team> teamList;
        if (page.isPresent()) {
            teamList = teamService.listTeams(
                    PageRequest.of(page.get(), limit.orElse(pagingProperties.getDefaultLimit())));
        } else {
            teamList = teamService.listTeams();
        }
        log.debug("Found teams: {}", teamList.size());
        return teamList;
    }

    @GetMapping("/getTheFood")
    public Team getTeamById(@RequestParam(value = "teamId") String teamId) {
        log.info("Received a request for food with id: {}", teamId);
        try {
            Team team = teamService.giveTheTeam(teamId);
            return team;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Team with id: {} not found.", teamId);
        }
        return null;
    }


}



