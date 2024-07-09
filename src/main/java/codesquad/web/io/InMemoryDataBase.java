package codesquad.web.io;

import codesquad.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryDataBase {

    private static final AtomicLong idCounter = new AtomicLong(1);
    private static final Map<Long, Object> store = new ConcurrentHashMap<>();

    private InMemoryDataBase() {
    }

    public static long generateId() {
        return idCounter.getAndIncrement();
    }

    public static void saveUser(final User user) {
        store.put(user.getId(), user);
    }

    public static Object findById(final long id) {
        return store.get(id);
    }
}
