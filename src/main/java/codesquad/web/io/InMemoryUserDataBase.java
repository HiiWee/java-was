package codesquad.web.io;

import codesquad.web.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserDataBase {

    private static final AtomicLong idCounter = new AtomicLong(1);
    private static final Map<Long, User> userStore = new ConcurrentHashMap<>();

    private InMemoryUserDataBase() {
    }

    public static long generateId() {
        return idCounter.getAndIncrement();
    }

    public static void saveUser(final User user) {
        userStore.put(user.getId(), user);
    }

    public static Optional<User> findByUserId(final String userId) {
        return userStore.values()
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }
}
