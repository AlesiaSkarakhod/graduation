package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.AuthorizedUser;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.repository.UserRepository;
import ru.javawebinar.graduation.to.UserTo;
import ru.javawebinar.graduation.util.UserUtil;

import java.util.List;

import static ru.javawebinar.graduation.util.UserUtil.prepareToSave;
import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @CacheEvict(value = "user", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    //    @CacheEvict(value = "user", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }
    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    //    @Cacheable("user")
    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    //    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
    }

    //    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = checkNotFoundWithId(get(id), id);
        user.setEnabled(enabled);
        userRepository.save(user);  // !! need only for JDBC implementation
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }
}