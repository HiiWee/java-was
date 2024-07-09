package codesquad.was.http;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {

    private final Map<String, String> cookieStore = new HashMap<>();

    public void setCookie(final String key, final String value) {
        cookieStore.put(key, value);
    }

    public String getCookie(final String key) {
        return cookieStore.get(key);
    }
}
