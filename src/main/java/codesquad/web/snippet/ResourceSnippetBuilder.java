package codesquad.web.snippet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceSnippetBuilder {

    private String templatePath;
    private String snippet;
    private List<String> attributes = new ArrayList<>();

    public static ResourceSnippetBuilder builder() {
        return new ResourceSnippetBuilder();
    }

    public ResourceSnippetBuilder templatePath(final String templatePath) {
        this.templatePath = templatePath;

        return this;
    }

    public ResourceSnippetBuilder snippet(final String snippet) {
        this.snippet = snippet;

        return this;
    }

    public ResourceSnippetBuilder addAttribute(final String attribute) {
        attributes.add(attribute);

        return this;
    }

    public ResourceSnippet build() throws IOException {
        return new ResourceSnippet(templatePath, snippet, attributes);
    }
}
