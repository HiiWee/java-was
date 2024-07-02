package codesquad.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Headers {

    private static final int HEADER_NAME_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private final Map<String, String> headers;

    public Headers(final Map<String, String> headers) {
        this.headers = headers;
    }

    public Headers(final BufferedReader requestReader) throws IOException {
        Map<String, String> requestHeaderFields = new HashMap<>();

        String headerLine;
        while (!(headerLine = requestReader.readLine()).isEmpty()) {
            String[] headerSplits = headerLine.split(":");
            requestHeaderFields.put(headerSplits[HEADER_NAME_INDEX].trim(), headerSplits[HEADER_VALUE_INDEX].trim());
        }

        headers = requestHeaderFields;
    }

    public String getHeader(final String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
