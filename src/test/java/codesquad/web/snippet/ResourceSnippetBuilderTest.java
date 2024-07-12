package codesquad.web.snippet;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class ResourceSnippetBuilderTest {

    @Test
    void ResourceSnippetBuilder를_통해_외부_템플릿_파일에_스니펫을_매핑_할_수_있다() throws IOException {
        // when
        ResourceSnippet resourceSnippet = ResourceSnippetBuilder.builder()
                .templatePath("/test-template.txt")
                .snippets(List.of(new Snippet("Hello"), new Snippet("World")))
                .build();

        // then
        assertThat(resourceSnippet.getCompleteSnippet()).isEqualTo("Hello World!");
    }
}
