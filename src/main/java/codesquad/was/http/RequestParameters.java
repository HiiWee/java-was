package codesquad.was.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParameters {

    public static final int KEY_VALUE_LENGTH = 2;

    private final Map<String, String> requestParameters = new HashMap<>();

    public void putParameters(final String bodyData) {
        requestParameters.putAll(parseQueryParams(bodyData));
    }

    private Map<String, String> parseQueryParams(final String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("="))
                .filter(params -> params.length == KEY_VALUE_LENGTH)
                .collect(Collectors.toMap(splitParams -> splitParams[0], splitParams -> splitParams[1]));
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
