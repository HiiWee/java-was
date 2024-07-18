package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.was.http.type.MimeType;
import java.io.UnsupportedEncodingException;

public class RequestMessageBody {

    private final byte[] bodyData;
    private final MimeType mimeType;
    private final RequestParameters formParameters = new RequestParameters();

    public RequestMessageBody(final byte[] bodyData, final MimeType mimeType) throws UnsupportedEncodingException {
        this.bodyData = bodyData;
        this.mimeType = mimeType;

        if (mimeType == MimeType.APPLICATION_X_WWW_FORM_ENCODED) {
            formParameters.putParameters(new String(bodyData, UTF_8.getCharset()));
        }
        // 멀티파트 파싱 구현
    }

    public boolean containsParameter(final String key) {
        return formParameters.contains(key);
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public String getParameter(final String key) {
        return formParameters.get(key);
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "bodyData='" + bodyData + '\'' +
                '}';
    }
}
