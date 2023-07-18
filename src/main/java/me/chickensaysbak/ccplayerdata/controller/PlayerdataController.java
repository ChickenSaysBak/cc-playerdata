package me.chickensaysbak.ccplayerdata.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chickensaysbak.ccplayerdata.model.Playerdata;
import me.chickensaysbak.ccplayerdata.model.Rank;
import me.chickensaysbak.ccplayerdata.repository.PlayerdataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/playerdata")
@CrossOrigin
@Tag(name = "Playerdata Endpoints")
public class PlayerdataController {

    private final PlayerdataRepository repository;

    public PlayerdataController(PlayerdataRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(description = "Retrieves every player that has ever joined CozyCloud.")
    public List<Playerdata> findAll(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAll(getPageable(limit));
    }

    @GetMapping("/first_joined")
    @Operation(description = "Retrieves every player ordered by who joined first.")
    public List<Playerdata> findByFirstJoined(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOrderByFirstPlayed(getPageable(limit));
    }

    @GetMapping("/last_joined")
    @Operation(description = "Retrieves every player ordered by who joined last.")
    public List<Playerdata> findByLastJoined(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOrderByFirstPlayedDesc(getPageable(limit));
    }

    @GetMapping("/year/{year}")
    @Operation(description = "Retrieves every player who joined during a certain year.")
    public List<Playerdata> findByYearJoined(@PathVariable Integer year, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllBetweenTimes(getMillisFromYear(year), getMillisFromYear(year+1), getPageable(limit));
    }

    @GetMapping("/most_time")
    @Operation(description = "Retrieves every player ordered by who has the largest time span.")
    public List<Playerdata> findByMostTime(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByMostTime(getPageable(limit));
    }

    @GetMapping("/least_time")
    @Operation(description = "Retrieves every player ordered by who has the smallest time span.")
    public List<Playerdata> findByLeastTime(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByLeastTime(getPageable(limit));
    }

    @GetMapping("/rank")
    @Operation(description = "Retrieves every player who has a rank.")
    public List<Playerdata> findRanked(@RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllRanked(getPageable(limit));
    }

    @GetMapping("/rank/{rank}")
    @Operation(description = "Retrieves every player who has a certain rank.")
    public List<Playerdata> findByRank(@Parameter(description = "Can be the rank name or a number 0-5 with 0 representing no rank. " +
            "Rank names are none, egg, chick, chicken, elder, and cloudian.") @PathVariable String rank, @RequestParam(required = false, defaultValue = "0") Integer limit) {

        Rank r;

        try {
            r = Rank.fromString(rank);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("That rank doesn't exist.");
        }

        return repository.findAllByRank(r.ordinal(), getPageable(limit));

    }

    @GetMapping("/uuid/{uuid}")
    @Operation(description = "Retrieves the player with the matching uuid.")
    public Playerdata findByUuid(@PathVariable UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> new NoSuchElementException("That player was not found."));
    }

    @GetMapping("/username/{username}")
    @Operation(description = "Retrieves any players with a matching username. " +
            "This should almost never return more than 1 value, though it is technically possible.")
    public List<Playerdata> findByUsername(@PathVariable String username, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findByUsername(username, getPageable(limit));
    }

    @GetMapping("/search/{keyword}")
    @Operation(description = "Retrieves any players with a username containing the keyword.")
    public List<Playerdata> findByUsernameSearch(@PathVariable String keyword, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByUsernameContainingIgnoreCase(keyword, getPageable(limit));
    }

    @GetMapping("/overlap/{uuid}")
    @Operation(description = "Retrieves any players with time spans overlapping the player specified by uuid. " +
            "The resulting players are ordered by most similar time span.")
    public List<Playerdata> findByOverlap(@PathVariable UUID uuid, @RequestParam(required = false, defaultValue = "0") Integer limit) {
        return repository.findAllByOverlap(uuid, getPageable(limit));
    }

    private Pageable getPageable(Integer limit) {
        return limit > 0 ? PageRequest.ofSize(limit) : Pageable.unpaged();
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
