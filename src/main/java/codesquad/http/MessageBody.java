package codesquad.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class MessageBody {

    private static final String EMPTY_DATA = "";
    private static final int END_STREAM = -1;
    private static final int NO_DATA = 0;

    private final String bodyData;

    public MessageBody(final String bodyData) {
        this.bodyData = bodyData;
    }

    public MessageBody(final BufferedReader requestReader, final String contentLengthValue) throws IOException {
        if (Objects.isNull(contentLengthValue) || "0".equals(contentLengthValue.trim())) {
            bodyData = EMPTY_DATA;
            return;
        }
        bodyData = parseRequestMessageBody(requestReader, contentLengthValue);
    }

    public MessageBody(final byte[] bytes) {
        bodyData = new String(bytes);
    }

    private String parseRequestMessageBody(final BufferedReader requestReader,
                                           final String contentLengthValue) throws IOException {

        int contentLength = Integer.parseInt(contentLengthValue.trim());
        char[] buffer = new char[contentLength];
        int result = requestReader.read(buffer);
        if (result == NO_DATA || result == END_STREAM) {
            return EMPTY_DATA;
        }

        return String.valueOf(buffer)
                .trim();
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
