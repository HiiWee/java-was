package codesquad.was.http.type;

import java.util.Arrays;
import java.util.Set;

public enum MimeType {

    HTML(Set.of("html"), "text/html"),
    CSS(Set.of("css"), "text/css"),
    JAVASCRIPT(Set.of("js"), "text/javascript"),
    ICO(Set.of("ico"), "image/x-icon"),
    PNG(Set.of("png"), "image/png"),
    JPG(Set.of("jpg", "jpeg"), "image/jpeg"),
    SVG(Set.of("svg"), "image/svg+xml"),
    APPLICATION_X_WWW_FORM_ENCODED(Set.of("application/x-www-form-urlencoded"), "application/x-www-form-urlencoded"),
    APPLICATION_OCTET_STREAM(Set.of("application/octet-stream"), "application/octet-stream");

    private final Set<String> extensions;
    private final String value;

    MimeType(final Set<String> extensions, final String value) {
        this.extensions = extensions;
        this.value = value;
    }

    public static String findMimeValue(final String extension) {
        return Arrays.stream(values())
                .filter(type -> type.extensions.contains(extension))
                .map(type -> type.value)
                .findAny()
                .orElse(APPLICATION_OCTET_STREAM.value);
    }

    public String getValue() {
        return value;
    }
}
