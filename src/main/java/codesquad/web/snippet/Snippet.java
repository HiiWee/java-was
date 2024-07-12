package codesquad.web.snippet;

import java.util.List;
import java.util.stream.Collectors;

public class Snippet {

    private final String completeSnippet;

    public Snippet(final String completeSnippet) {
        this.completeSnippet = completeSnippet;
    }

    public Snippet(final String snippet, final List<String> attributes) {
        completeSnippet = String.format(snippet, attributes.toArray());
    }

    public static Snippet combineAll(final List<Snippet> snippets) {
        return new Snippet(snippets.stream()
                .map(snippet -> snippet.completeSnippet)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    public String getCompleteSnippet() {
        return completeSnippet;
    }
}
