package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.repository.RestaurantRepository;
import ru.javawebinar.graduation.repository.VoteRepository;
import ru.javawebinar.graduation.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VoteRepository voteRepository;

    //    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant save = restaurantRepository.save(restaurant);
        return checkNotFoundWithId(save, save.id());
    }

    //    @CacheEvict(value = "restaurant", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    //    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

//    @Cacheable("restaurant")
    public List<RestaurantTo> getAll() {
        return restaurantRepository.getAll()
                .stream()
                .map(r -> new RestaurantTo(r.getId(), r.getName(), voteRepository.countVoteByDate(r.id())))
                .collect(Collectors.toList());
    }

}
