package codesquad.was.http;

import java.util.Objects;

public class Cookie {

    private final String key;
    private final String value;
    private boolean isHttpOnly;
    private long maxAge = -1;
    private String path;

    public Cookie(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public boolean isKey(final String sid) {
        return key.equals(sid);
    }

    public void setHttpOnly(final boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }

    public void setMaxAge(final long maxAge) {
        this.maxAge = maxAge;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(key)
                .append("=")
                .append(value);

        if (isHttpOnly) {
            messageBuilder.append("; HttpOnly");
        }
        if (maxAge >= 0) {
            messageBuilder.append("; Max-Age=")
                    .append(maxAge);
        }
        if (path != null) {
            messageBuilder.append("; Path=")
                    .append(path);
        }

        return messageBuilder.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cookie cookie = (Cookie) o;
        return Objects.equals(key, cookie.key) && Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(key);
        result = 31 * result + Objects.hashCode(value);
        return result;
    }
}
