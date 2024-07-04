package codesquad.http.type;

import java.util.Arrays;

// TODO 표준, 비표준 헤더에 대한 정리 필요, 인덴트라도
public enum HeaderType {

    NONE("none"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    HOST("Host"),
    CONNECTION("Connection"),
    CACHE_CONTROL("Cache-Control"),
    SEC_CH_UA("Sec-Ch-UA"),
    SEC_CH_UA_MOBILE("Sec-Ch-UA-Mobile"),
    SEC_CH_UA_PLATFORM("Sec-Ch-UA-Platform"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    REFERER("Referer"),
    COOKIE("Cookie"),
    LOCATION("Location");

    private final String headerName;

    HeaderType(final String headerName) {
        this.headerName = headerName;
    }

    public static HeaderType find(final String inputHeaderName) {
        return Arrays.stream(values())
                .filter(type -> type.headerName.equalsIgnoreCase(inputHeaderName))
                .findAny()
                .orElse(NONE);
    }

    public String getHeaderName() {
        return headerName;
    }

    @Override
    public String toString() {
        return headerName;
    }
}
