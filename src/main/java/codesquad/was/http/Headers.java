package codesquad.was.http;

import codesquad.was.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Headers {

    private static final int HEADER_NAME_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<HeaderType, String> headers = new LinkedHashMap<>();

    public Headers() {
    }

    public Headers(final BufferedReader requestReader) throws IOException {
        Map<HeaderType, String> requestHeaderFields = new LinkedHashMap<>();

        String headerLine;

        while (!(headerLine = requestReader.readLine()).isEmpty()) {
            String[] headerSplits = headerLine.split(":");
            HeaderType headerType = HeaderType.find(headerSplits[HEADER_NAME_INDEX].trim());

            if (headerType == HeaderType.NONE) {
                log.warn("{} 헤더를 찾지 못했습니다.", headerSplits[HEADER_NAME_INDEX].trim());
                continue;
            }
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
