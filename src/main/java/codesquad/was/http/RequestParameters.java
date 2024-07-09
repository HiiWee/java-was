package codesquad.was.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParameters {

    public static final int KEY_VALUE_LENGTH = 2;

    private final Map<String, String> requestParameters = new HashMap<>();

    public void putParameters(final String bodyData) throws UnsupportedEncodingException {
        String decodedBodyData = URLDecoder.decode(bodyData, "UTF-8");
        requestParameters.putAll(parseQueryParams(decodedBodyData));
    }

    private Map<String, String> parseQueryParams(final String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("="))
                .filter(params -> params.length == KEY_VALUE_LENGTH)
                .collect(Collectors.toMap(splitParams -> splitParams[0].trim(), splitParams -> splitParams[1].trim()));
    }

    public String get(final String name) {
        return requestParameters.get(name);
    }

    @Override
    public String toString() {
        return "RequestParameters{" +
                "requestParameters=" + requestParameters +
                '}';
    }
}
