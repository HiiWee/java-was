package codesquad.web.snippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceSnippetBuilder {

    private String templatePath;
    private List<Snippet> snippets = new ArrayList<>();

    public static ResourceSnippetBuilder builder() {
        return new ResourceSnippetBuilder();
    }

    public ResourceSnippetBuilder templatePath(final String templatePath) {
        this.templatePath = templatePath;

        return this;
    }

    public ResourceSnippetBuilder snippets(final List<Snippet> snippets) {
        this.snippets.addAll(snippets);

        return this;
    }

    public ResourceSnippet build() throws IOException {
        return new ResourceSnippet(templatePath, snippets);
    }
}
