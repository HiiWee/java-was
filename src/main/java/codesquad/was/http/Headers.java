package codesquad.was.http;

import codesquad.was.http.type.HeaderType;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Headers {

    private static final int HEADER_NAME_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final String CRLF = "\r\n";
    private static final String DELIMITER = ": ";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<HeaderType, List<String>> headers = new LinkedHashMap<>();

    public Headers() {
    }

    public Headers(final BufferedReader requestReader) throws IOException {
        Map<HeaderType, List<String>> requestHeaderFields = new LinkedHashMap<>();

        String headerLine;

        while (!(headerLine = requestReader.readLine()).isEmpty()) {
            String[] headerSplits = headerLine.split(":");
            HeaderType headerType = HeaderType.find(headerSplits[HEADER_NAME_INDEX].trim());

            if (headerType == HeaderType.NONE) {
                log.warn("{} 헤더를 찾지 못했습니다.", headerSplits[HEADER_NAME_INDEX].trim());
                continue;
            }
            requestHeaderFields.computeIfAbsent(headerType, k -> new ArrayList<>())
                    .add(headerSplits[HEADER_VALUE_INDEX].trim());
        }

        headers.putAll(requestHeaderFields);
    }


    public void add(final HeaderType headerType, final String value) {
        headers.computeIfAbsent(headerType, k -> new ArrayList<>())
                .add(value);
    }

    public void addCookies(final List<Cookie> cookies) {
        List<String> cookiesMessage = cookies.stream()
                .map(Cookie::getMessage)
                .toList();
        headers.put(HeaderType.SET_COOKIE, cookiesMessage);
    }

    public String createMessage() {
        if (headers.containsKey(HeaderType.SET_COOKIE)) {
            return createHeaderMessageWithoutCookie() + CRLF + createSetCookiesMessage();
        }
        return createHeaderMessageWithoutCookie();
    }

    private String createHeaderMessageWithoutCookie() {
        return headers.entrySet()
                .stream()
                .filter(entry -> entry.getKey() != HeaderType.SET_COOKIE)
                .map(entry -> entry.getKey().getHeaderName() + DELIMITER + String.join("; ", entry.getValue()))
                .collect(Collectors.joining(CRLF));
    }

    private String createSetCookiesMessage() {
        return headers.entrySet()
                .stream()
                .filter(entry -> entry.getKey() == HeaderType.SET_COOKIE)
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .map(value -> entry.getKey() + DELIMITER + value))
                .collect(Collectors.joining(CRLF));
    }

    public String getHeader(final HeaderType headerType) {
        List<String> headerValues = headers.get(headerType);
        if (headerValues == null) {
            return null;
        }

        return String.join("; ", headers.get(headerType));
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
