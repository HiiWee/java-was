package codesquad.web.snippet;

import java.util.ArrayList;
import java.util.List;

public class SnippetBuilder {

    private String snippet;
    private final List<String> attributes = new ArrayList<>();

    public static SnippetBuilder builder() {
        return new SnippetBuilder();
    }

    public SnippetBuilder snippet(final String snippet) {
        this.snippet = snippet;

        return this;
    }

    public SnippetBuilder attributes(final List<String> inputAttributes) {
        attributes.addAll(inputAttributes);

        return this;
    }

    public Snippet build() {
        return new Snippet(snippet, attributes);
    }
}
