package codesquad.was.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {

    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void setAttribute(final String key, final Object value) {
        sessionStore.put(key, value);
    }

    public Object getAttribute(final String key) {
        return sessionStore.get(key);
    }

    public void removeAttributes(final String value) {
        sessionStore.remove(value);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "sessionStore=" + sessionStore +
                '}';
    }
}
