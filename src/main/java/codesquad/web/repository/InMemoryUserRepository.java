package codesquad.web.repository;

import codesquad.web.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private static final Map<String, User> userStore = new ConcurrentHashMap<>();

    @Override
    public void save(final User user) {
        userStore.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findByUserId(final String userId) {
        return userStore.values()
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }
}
