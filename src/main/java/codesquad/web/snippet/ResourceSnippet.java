package codesquad.web.snippet;

import codesquad.was.exception.InternalServerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceSnippet {

    private final String completeSnippet;

    public ResourceSnippet(final String templatePath, final List<Snippet> snippets) {
        URL templateUrl = getClass().getClassLoader().getResource("templates" + templatePath);

        if (templateUrl == null) {
            throw new InternalServerException();
        }

        String templateContent = readTemplate(templateUrl);
        List<String> snippetValues = snippets.stream()
                .map(Snippet::getCompleteSnippet)
                .toList();

        completeSnippet = String.format(templateContent, snippetValues.toArray());
    }

    private String readTemplate(final URL fileUrl) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileUrl.openStream()))) {
            String content = fileReader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            contentBuilder.append(content);
        } catch (IOException e) {
            throw new InternalServerException();
        }
        return contentBuilder.toString();
    }

    public String getCompleteSnippet() {
        return completeSnippet;
    }
}
