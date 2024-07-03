package codesquad.http;

public class ResponseMessageBody {

    private final byte[] bytes;

    public ResponseMessageBody(final byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
