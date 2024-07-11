package codesquad.web.snippet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SnippetTest {

    @Test
    void 스니펫에_문자열_속성들을_매핑_할_수_있다() {
        // given
        String testSnippetFormat = "%s %s!";

        // when
        Snippet snippet = new Snippet(testSnippetFormat, List.of("Hello", "World"));

        // then
        assertThat(snippet.getCompleteSnippet()).isEqualTo("Hello World!");
    }

    @Test
    void 여러개의_스니펫을_개행문자를_추가하여_합칠_수_있다() {
        // given
        Snippet snippet1 = new Snippet("%s", List.of("Hello"));
        Snippet snippet2 = new Snippet("%s!", List.of("World"));

        // when
        Snippet snippet = Snippet.combineAll(List.of(snippet1, snippet2));

        // then
        assertThat(snippet.getCompleteSnippet()).isEqualTo("Hello\nWorld!");
    }
}
