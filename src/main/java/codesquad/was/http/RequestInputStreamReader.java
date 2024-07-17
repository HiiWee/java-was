package codesquad.was.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RequestInputStreamReader {

    private static final int CR = 13;
    private static final int LF = 10;

    private final byte[] bytes;
    private int offset;

    public RequestInputStreamReader(final BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.read();
        bytes = bufferedInputStream.readAllBytes();
    }

    public String readRequestLine() throws UnsupportedEncodingException {
        int count = countLine(LF);
        byte[] requestLineBytes = new byte[count - 1];

        System.arraycopy(bytes, offset, requestLineBytes, 0, count - 1);
        offset += count + 1;

        return new String(requestLineBytes, "UTF-8");
    }

    public List<String> readHeaders() throws UnsupportedEncodingException {
        List<String> headerValues = new ArrayList<>();

        int count;
        while ((count = countLine(LF)) > 1) {
            byte[] singleHeaderBytes = new byte[count - 1];
            System.arraycopy(bytes, offset, singleHeaderBytes, 0, count - 1);
            offset += count + 1;

            headerValues.add(new String(singleHeaderBytes, "UTF-8"));
        }
        int crlfCount = countLine(LF);
        System.out.println(bytes[offset]);
        offset += crlfCount + 1;
        System.out.println(bytes.length);
        System.out.println(offset);
        return headerValues;
    }

    private int countLine(final int specificByte) {
        return (int) IntStream.iterate(offset, i -> bytes[i] != specificByte, i -> i + 1).count();
    }
}

