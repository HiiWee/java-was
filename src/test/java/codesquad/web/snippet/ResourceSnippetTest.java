package codesquad.web.snippet;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class ResourceSnippetTest {

    @Test
    void 외부_템플릿_파일에_스니펫에_문자열_속성을_매핑_할_수_있다() throws IOException {
        // when
        ResourceSnippet resourceSnippet = new ResourceSnippet(
                "/test-template.txt",
                List.of(new Snippet("Hello"), new Snippet("World"))
        );

        // then
        assertThat(resourceSnippet.getCompleteSnippet()).isEqualTo("Hello World!");
    }
}
