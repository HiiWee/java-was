package codesquad.was.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

public class RequestMessageBody {

    private static final String EMPTY_DATA = "";
    private static final int END_STREAM = -1;
    private static final int NO_DATA = 0;

    private final String bodyData;

    public RequestMessageBody(final String bodyData) {
        this.bodyData = bodyData;
    }

    public RequestMessageBody(final BufferedReader requestReader, final String contentLengthValue) throws IOException {
        if (Objects.isNull(contentLengthValue) || "0".equals(contentLengthValue.trim())) {
            bodyData = EMPTY_DATA;
            return;
        }
        bodyData = parseRequestMessageBody(requestReader, contentLengthValue);
    }

    private String parseRequestMessageBody(final BufferedReader requestReader,
                                           final String contentLengthValue) throws IOException {
        int contentLength = Integer.parseInt(contentLengthValue.trim());
        char[] buffer = new char[contentLength];
        int result = requestReader.read(buffer);
        if (result == NO_DATA || result == END_STREAM) {
            return EMPTY_DATA;
        }
        return URLDecoder.decode(String.valueOf(buffer).trim(), "UTF-8");
    }

    public String getBodyData() {
        return bodyData;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "bodyData='" + bodyData + '\'' +
                '}';
    }
}
