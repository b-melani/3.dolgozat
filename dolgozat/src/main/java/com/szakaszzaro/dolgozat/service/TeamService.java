package com.szakaszzaro.dolgozat.service;

import com.szakaszzaro.dolgozat.controller.TeamController;
import com.szakaszzaro.dolgozat.entity.Team;
import com.szakaszzaro.dolgozat.exception.ValidationException;
import com.szakaszzaro.dolgozat.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team fromRequest) throws ValidationException {
        if (fromRequest.getName() == null || fromRequest.getName() == "") {
            throw new ValidationException("name can not be null or empty string!", HttpStatus.BAD_REQUEST);
        } else {
            log.info("Creating Team based on: {} ...", fromRequest);
            Team result = teamRepository.save(fromRequest);
            return result;
        }
    }

    public Team updateTeam(String teamId, Team team) throws ValidationException {
        log.info("Updating team on team id: {}, team: {}", teamId, team);
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isEmpty()) {
            throw new ValidationException("There is no such Id", HttpStatus.BAD_REQUEST);
        }
        Team actualTeam = optionalTeam.get();
        log.debug("Original team was: {}", actualTeam.getName());
        actualTeam.setName(team.getName());
        actualTeam.setKind(team.getKind());
        actualTeam.setUniverse(team.getUniverse());
        Team updated = teamRepository.save(actualTeam);
        log.debug("Updated team is: {}", updated);
        return updated;
    }

    public List<Team> listTeams(Pageable pageable) {
        log.info("Listing teams (page information: {}) ...", pageable);

        Page<Team> teamPage = teamRepository.findAll(pageable);
        List<Team> foodList = teamPage.getContent();
        log.debug("Total count: {}, total pages: {}", teamPage.getTotalElements(), teamPage.getTotalPages());

        return foodList;
    }

    public List<Team> listTeams() {
        log.info("Listing all teams..");
        List<Team> teamList = teamRepository.findAll();
        log.debug("Total count: {}", teamList.size());
        return teamList;
    }

    public Team giveTheTeam(String teamId) throws Exception {
        log.info("Received a request on id: {}.", teamId);
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isEmpty()) {
            log.error ("There is no team with id: {}.", teamId);
            throw new ValidationException("There is no team with sutch id. ");
        }
        Team team = optionalTeam.get();
        log.debug("The team for id {} is: {}", teamId, team);
        return team;
    }
}
