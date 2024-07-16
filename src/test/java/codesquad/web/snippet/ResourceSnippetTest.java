package codesquad.web.snippet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import codesquad.was.exception.InternalServerException;
import java.util.List;
import org.junit.jupiter.api.Test;

class ResourceSnippetTest {

    @Test
    void 외부_템플릿_파일에_스니펫에_문자열_속성을_매핑_할_수_있다() {
        // when
        ResourceSnippet resourceSnippet = new ResourceSnippet(
                "/test-template.txt",
                List.of(new Snippet("Hello"), new Snippet("World"))
        );

        // then
        assertThat(resourceSnippet.getCompleteSnippet()).isEqualTo("Hello World!");
    }

    @Test
    void 외부_템플릿_파일이_존재하지_않는다면_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> new ResourceSnippet("/invalid.txt", List.of(new Snippet("Hello"))))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("서버에서 요청을 처리할 수 없습니다.");
    }
}
