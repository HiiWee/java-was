package codesquad.was.http;

import codesquad.was.http.type.HeaderType;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Headers(final List<String> headerValues) {
        headers.putAll(headerValues.stream()
                .map(this::parseKeyAndValue)
                .filter(headers -> HeaderType.find(headers[HEADER_NAME_INDEX]) != HeaderType.NONE)
                .collect(Collectors.groupingBy(
                        headers -> HeaderType.find(headers[HEADER_NAME_INDEX].trim()),
                        Collectors.flatMapping(
                                headers -> Arrays.stream(headers[HEADER_VALUE_INDEX].split(";")).map(String::trim),
                                Collectors.toList()
                        )
                )));
        System.out.println(headers);
    }

    private String[] parseKeyAndValue(final String header) {
        String[] splits = header.split(":");

        if (splits.length > 2) {
            String[] newSplits = new String[2];
            newSplits[HEADER_NAME_INDEX] = splits[HEADER_NAME_INDEX];
            String valueFormat = "%s" + ":%s".repeat(splits.length - 2);
            String[] args = Arrays.copyOfRange(splits, 1, splits.length);
            newSplits[HEADER_VALUE_INDEX] = String.format(valueFormat, args);

            return newSplits;
        }
        return splits;
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

    public List<String> getHeader(final HeaderType headerType) {
        return headers.get(headerType);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "headers=" + headers +
                '}';
    }
}
