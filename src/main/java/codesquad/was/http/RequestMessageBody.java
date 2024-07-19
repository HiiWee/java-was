package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.was.http.type.MimeType;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class RequestMessageBody {

    private final byte[] bodyData;
    private final MimeType mimeType;
    private MultipartParser multipartParser;
    private MultipartFile multipartFile;

    private final RequestParameters formParameters = new RequestParameters();


    public RequestMessageBody(final byte[] bodyData, final MimeType mimeType,
                              Supplier<List<String>> contentTypeSupplier) throws IOException {
        this.bodyData = bodyData;
        this.mimeType = mimeType;

        if (mimeType == MimeType.APPLICATION_X_WWW_FORM_ENCODED) {
            formParameters.putParameters(new String(bodyData, UTF_8.getCharset()));
        }
        if (mimeType == MimeType.MULTIPART_FORM_DATA) {
            multipartParser = new MultipartParser(bodyData, contentTypeSupplier.get());
            formParameters.putAll(multipartParser.getFormParameters());
            multipartFile = multipartParser.getMultipartFile();
        }
    }

    public boolean containsParameter(final String key) {
        return formParameters.contains(key);
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public String getParameter(final String key) {
        return formParameters.get(key);
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "bodyData='" + bodyData + '\'' +
                '}';
    }
}
