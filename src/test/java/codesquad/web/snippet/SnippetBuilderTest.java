package codesquad.web.snippet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SnippetBuilderTest {

    @Test
    void SnippetBuilder를_통해_스니펫을_생성할_수_있다() {
        // when
        Snippet snippet = SnippetBuilder.builder()
                .snippet("%s %s!")
                .attributes(List.of("Hello", "World"))
                .build();

        // then
        assertThat(snippet.getCompleteSnippet()).isEqualTo("Hello World!");
    }
}
