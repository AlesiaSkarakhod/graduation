package ru.javawebinar.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.model.Vote;
import ru.javawebinar.graduation.repository.RestaurantRepository;
import ru.javawebinar.graduation.repository.UserRepository;
import ru.javawebinar.graduation.repository.VoteRepository;
import ru.javawebinar.graduation.to.VoteTo;
import ru.javawebinar.graduation.util.exception.NotChangeVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.graduation.util.ValidationUtil.*;

@Service
public class VoteService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String NOT_CHANGE_VOTE = "It is too late, vote can't be changed";

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public Vote create(int restaurantId, int userId) {
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        Vote vote = voteRepository.getByDateAndUser(LocalDate.now(), userId);
        if (vote != null) {
            if (vote.afterEleven()) {
                log.info("For restaurant {} by user {} vote can't be changed", restaurantId, userId);
                throw new NotChangeVoteException(NOT_CHANGE_VOTE);
            } else {
                vote.setDate(LocalDate.now());
                vote.setTime(LocalTime.now());
                vote.setId(vote.getId());
                vote.setRestaurant(restaurant);
            }
        }
        User user = userRepository.getOne(userId);
        vote = new Vote(LocalDate.now(), LocalTime.now(), restaurant, user);
        return voteRepository.save(vote);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    @Transactional
    public List<VoteTo> getAll(int userId) {
        return voteRepository.getAllByUserId(userId)
                .stream()
                .map(v -> new VoteTo(v.getId(), v.getDate(), v.getTime(), v.getRestaurant().getName(), v.getUser()))
                .collect(Collectors.toList());
    }

    public Vote getByDate(LocalDate date, int userId) {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(voteRepository.getByDateAndUser(date, userId),
                String.format("date=%s, userId=%d", date.toString(), userId));
    }

}
