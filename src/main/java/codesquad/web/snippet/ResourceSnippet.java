package codesquad.web.snippet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceSnippet {

    private final String completeSnippet;

    public ResourceSnippet(final String templatePath, final List<Snippet> snippets) throws IOException {
        URL templateUrl = getClass().getClassLoader().getResource("templates" + templatePath);

        if (templateUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다. templatePath = " + templatePath);
        }

        String templateContent = readTemplate(templateUrl);
        List<String> snippetValues = snippets.stream()
                .map(Snippet::getCompleteSnippet)
                .toList();

        completeSnippet = String.format(templateContent, snippetValues.toArray());
    }

    private static String readTemplate(final URL fileUrl) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileUrl.openStream()))) {
            String content = fileReader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            contentBuilder.append(content);
        }
        return contentBuilder.toString();
    }

    public String getCompleteSnippet() {
        return completeSnippet;
    }
}
