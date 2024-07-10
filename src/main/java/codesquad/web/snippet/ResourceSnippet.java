package codesquad.web.snippet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class ResourceSnippet {

    private final String completeSnippet;

    public ResourceSnippet(final String templatePath, final String snippet, final List<String> attributes)
            throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource("templates" + templatePath);

        if (fileUrl == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다. templatePath = " + templatePath);
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileUrl.openStream()))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                contentBuilder.append(line);
                contentBuilder.append(System.lineSeparator());
            }
        }
        completeSnippet = String.format(contentBuilder.toString(), joinAttribute(snippet, attributes));
    }

    private String joinAttribute(final String snippet, final List<String> attributes) {
        return String.format(snippet, attributes.toArray(new String[0]));
    }

    public String getCompleteSnippet() {
        return completeSnippet;
    }
}
