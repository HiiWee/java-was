package codesquad.http;

import codesquad.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Headers {

    private static final int HEADER_NAME_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private final Map<HeaderType, String> headers = new LinkedHashMap<>();

    public Headers() {
    }

    public Headers(final BufferedReader requestReader) throws IOException {
        Map<HeaderType, String> requestHeaderFields = new LinkedHashMap<>();

        String headerLine;
        while (!(headerLine = requestReader.readLine()).isEmpty()) {
            String[] headerSplits = headerLine.split(":");
            requestHeaderFields.put(HeaderType.find(headerSplits[HEADER_NAME_INDEX].trim()),
                    headerSplits[HEADER_VALUE_INDEX].trim());
        }

        headers.putAll(requestHeaderFields);
    }

    public void add(final HeaderType headerType, final String value) {
        headers.put(headerType, value);
    }

    public Map<HeaderType, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getHeader(final HeaderType headerType) {
        return headers.get(headerType);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
