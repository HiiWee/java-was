package codesquad.was.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

class RequestParametersTest {

    @Test
    void Query_String_형식의_파라미터를_저장할_수_있다() throws UnsupportedEncodingException {
        // given
        RequestParameters requestParameters = new RequestParameters();
        String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        requestParameters.putParameters(data);

        // then
        assertAll(
                () -> assertThat(requestParameters.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestParameters.get("name")).isEqualTo("박재성"),
                () -> assertThat(requestParameters.get("password")).isEqualTo("password"),
                () -> assertThat(requestParameters.get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
