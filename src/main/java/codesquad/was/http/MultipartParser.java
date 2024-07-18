package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.was.http.type.MimeType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultipartParser {

    private static final byte CR = 13;
    private static final String DASH = "--";
    private final String splitBoundary;
    private final String endBoundary;
    private final Map<String, String> formParameters = new HashMap<>();
    private int offset;

    public MultipartParser(final byte[] bodyData, final List<String> mimeTypes) throws IOException {
        System.out.println(new String(bodyData));

        // boundary 만들기
        String boundarySymbol = getBoundarySymbol(mimeTypes);
        splitBoundary = DASH + boundarySymbol;
        endBoundary = DASH + boundarySymbol + DASH;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bodyData);

        while (!new String(readBytesUntilSymbol(CR, inputStream), UTF_8.getCharset()).equals(endBoundary)) {
            Map<String, String> contentDispositionValues = getContentDispositionValues(
                    new String(readBytesUntilSymbol(CR, inputStream), UTF_8.getCharset()));

            // 그냥 form데이터
            if (contentDispositionValues.size() == 1) {
                inputStream.skip(2);
                String formKey = contentDispositionValues.get("name");
                String formValue = new String(readBytesUntilSymbol(CR, inputStream));
                formParameters.put(formKey, formValue);
            }
            if (contentDispositionValues.size() == 2) {
                MimeType mimeType = getMimeType(new String(readBytesUntilSymbol(CR, inputStream), UTF_8.getCharset()));
                System.out.println(mimeType);

                if (mimeType == MimeType.APPLICATION_OCTET_STREAM || contentDispositionValues.get("filename").isEmpty()) {
                    inputStream.skip(4);
                }
            }
        }

        System.out.println(formParameters);

//        byte[] bytes =
//
//

        // 각 섹션에서 Context-Disposition 읽고
        // 만약 다음줄이 CRLF가 아니면, 파일을 읽을 준비를 한다.

    }

    private MimeType getMimeType(final String contentAndMimeType) throws IOException {
        String[] contentAndMimeTypes = contentAndMimeType.split(":");

        return MimeType.findMimeType(contentAndMimeTypes[1].trim());
    }

    private Map<String, String> getContentDispositionValues(final String header) throws IOException {
        String wholeValue = header.split(":")[1];

        String[] values = wholeValue.split(";");
        String[] onlyValues = Arrays.copyOfRange(values, 1, values.length);

        return Arrays.stream(onlyValues)
                .map(onlyValue -> onlyValue.split("="))
                .collect(Collectors.toMap(
                        split -> split[0].trim(),
                        split -> split[1].trim().substring(1, split[1].trim().length() - 1)
                ));
    }

    private String getBoundarySymbol(final List<String> mimeTypes) {
        return mimeTypes.get(1).split("=")[1];
    }

    private byte[] readBytesUntilSymbol(final byte symbol, final InputStream inputStream) throws IOException {
        byte[] bytes;
        inputStream.mark(0);
        int count = countLine(inputStream, symbol);
        inputStream.reset();

        bytes = new byte[count];
        inputStream.read(bytes);
        skipByte(inputStream, 2);

        return bytes;
    }

    private int countLine(final InputStream inputStream, final byte specificByte) throws IOException {
        int count = 0;
        while ((inputStream.read()) != specificByte) {
            count++;
        }
        return count;
    }

    private void skipByte(final InputStream inputStream, int count) throws IOException {
        inputStream.skip(count);
    }

//    private byte[] countLineUntil(final byte symbol, final ByteArrayInputStream bodyData) {
//        int count = 0;
//        for (byte data : bodyData) {
//            if (data == symbol) {
//                break;
//            }
//            count++;
//        }
//        offset += count + 2;
//        return Arrays.copyOfRange(bodyData, 0, count);
//    }

    public Map<String, String> getFormParameters() {
        return null;
    }
}
