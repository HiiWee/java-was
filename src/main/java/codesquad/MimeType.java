package codesquad;

import java.util.Set;

public enum MimeType {

    HTML(Set.of(".html"), "text/html"),
    CSS(Set.of(".css"), "text/css"),
    JAVASCRIPT(Set.of(".js"), "text/javascript"),
    ICO(Set.of(".ico"), "image/vnd.microsoft.icon"),
    PNG(Set.of(".png"), "image/png"),
    JPG(Set.of(".jpg", "jpeg"), "image/jpeg");

    private final Set<String> extensions;
    private final String value;

    MimeType(final Set<String> extensions, final String value) {
        this.extensions = extensions;
        this.value = value;
    }
}
