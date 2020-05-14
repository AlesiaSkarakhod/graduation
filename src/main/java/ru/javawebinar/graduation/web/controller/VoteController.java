package ru.javawebinar.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.service.VoteService;
import ru.javawebinar.graduation.to.VoteTo;
import ru.javawebinar.graduation.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping("/user/{userId}")
    public List<VoteTo> getVotes() {
        log.info("getAll votes");
        int userId = SecurityUtil.authUserId();
        return voteService.getAll(userId);
    }

    @PostMapping("/restaurant/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void getVoteRestaurant(@PathVariable("restaurantId") int restaurantId) {
        log.info("vote for restaurant");
        int userId = SecurityUtil.authUserId();
        voteService.create(restaurantId, userId);
    }

    @DeleteMapping(value = "/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("voteId") int voteId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote {} by user {}", voteId, userId);
        voteService.delete(voteId, userId);
    }

    @GetMapping
    public Vote getOneByCurrentDay() {
        int userId = SecurityUtil.authUserId();
        log.info("get vote for current date {} by user {}", LocalDate.now(), userId);
        return voteService.getByDate(LocalDate.now(), userId);
    }

}
