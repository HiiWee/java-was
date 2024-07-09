package codesquad.was.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {

    private static final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void setAttribute(final String key, final Object value) {
        sessionStore.put(key, value);
    }

    public Object getAttribute(final String key) {
        return sessionStore.get(key);
    }
}
