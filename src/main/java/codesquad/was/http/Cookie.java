package codesquad.was.http;

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

    public void setHttpOnly(final boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }

    public void setMaxAge(final long maxAge) {
        this.maxAge = maxAge;
    }

    public void setPath(final String path) {
        this.path = path;
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
}
