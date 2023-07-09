package me.chickensaysbak.ccplayerdata.controller;

import me.chickensaysbak.ccplayerdata.model.Playerdata;
import me.chickensaysbak.ccplayerdata.model.Rank;
import me.chickensaysbak.ccplayerdata.repository.PlayerdataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/playerdata")
@CrossOrigin
public class PlayerdataController {

    private final PlayerdataRepository repository;

    public PlayerdataController(PlayerdataRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Playerdata> findAll(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAll(getPageRequest(limit));
    }

    @GetMapping("/first_joined")
    public List<Playerdata> findByFirstJoined(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOrderByFirstPlayed(getPageRequest(limit));
    }

    @GetMapping("/last_joined")
    public List<Playerdata> findByLastJoined(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOrderByFirstPlayedDesc(getPageRequest(limit));
    }

    @GetMapping("/year/{year}")
    public List<Playerdata> findByYearJoined(@PathVariable Integer year, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllBetweenTimes(getMillisFromYear(year), getMillisFromYear(year+1), getPageRequest(limit));
    }

    @GetMapping("/most_time")
    public List<Playerdata> findByMostTime(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByMostTime(getPageRequest(limit));
    }

    @GetMapping("/least_time")
    public List<Playerdata> findByLeastTime(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByLeastTime(getPageRequest(limit));
    }

    @GetMapping("/rank")
    public List<Playerdata> findRanked(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllRanked(getPageRequest(limit));
    }

    @GetMapping("/rank/{rank}")
    public List<Playerdata> findByRank(@PathVariable String rank, @RequestParam(required = false, defaultValue = "0") Integer limit) {

        Rank r;

        try {
            r = Rank.fromString(rank);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That rank doesn't exist.");
        }

        return repository.findAllByRank(r.ordinal(), getPageRequest(limit));

    }

    @GetMapping("/uuid/{uuid}")
    public Playerdata findByUuid(@PathVariable UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "That player was not found."));
    }

    @GetMapping("/username/{username}")
    public List<Playerdata> findByUsername(@PathVariable String username, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findByUsername(username, getPageRequest(limit));
    }

    @GetMapping("/search/{search}")
    public List<Playerdata> findByUsernameSearch(@PathVariable String search, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByUsernameContainingIgnoreCase(search, getPageRequest(limit));
    }

    @GetMapping("/overlap/{uuid}")
    public List<Playerdata> findByOverlap(@PathVariable UUID uuid, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOverlap(uuid, getPageRequest(limit));
    }

    private PageRequest getPageRequest(Integer limit) {
        return limit > 0 ? PageRequest.ofSize(limit) : null;
    }

    private Long getMillisFromYear(Integer year) {

        return switch (year) {

            case 2016 -> 1451624400000L;
            case 2017 -> 1483246800000L;
            case 2018 -> 1514782800000L;
            case 2019 -> 1546318800000L;
            case 2020 -> 1577854800000L;
            case 2021 -> 1609477200000L;
            case 2022 -> 1641013200000L;
            case 2023 -> 1672549200000L;
            case 2024 -> 1704085200000L;
            default -> 0L;

        };

    }

}
