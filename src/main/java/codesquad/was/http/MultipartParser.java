package codesquad.was.http;

import static codesquad.was.http.type.CharsetType.UTF_8;

import codesquad.utils.StringUtils;
import codesquad.was.http.type.MimeType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipartParser {


    private static final String CRLF = "\r\n";
    private static final byte CR = 13;
    private static final String DASH = "--";
    private static final int ONLY_FORM_DATA_SIZE = 1;
    private static final int MIME_DATA_SIZE = 2;

    private final Logger log = LoggerFactory.getLogger(MultipartParser.class);

    private final String splitBoundary;
    private final String endBoundary;
    private final Map<String, String> formParameters = new HashMap<>();
    private MultipartFile multipartFile;

    public MultipartParser(final byte[] bodyData, final List<String> mimeTypeValues) throws IOException {
        // boundary 만들기
        String boundarySymbol = getBoundarySymbol(mimeTypeValues);
        splitBoundary = DASH + boundarySymbol;
        endBoundary = DASH + boundarySymbol + DASH;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bodyData);

        while (!(new String(readUntilSymbol(CR, inputStream), UTF_8.getCharset())).equals(endBoundary)) {
            Map<String, String> contentDispositionValues = getContentDispositions(
                    new String(readUntilSymbol(CR, inputStream), UTF_8.getCharset()));

            // 그냥 form데이터
            if (contentDispositionValues.size() == ONLY_FORM_DATA_SIZE) {
                inputStream.skip(2);
                String formKey = contentDispositionValues.get("name");
                String formValue = new String(readUntilSymbol(CR, inputStream));
                formParameters.put(formKey, formValue);
            }
            // byte 데이터가 입력되면 filename,
            if (contentDispositionValues.size() == MIME_DATA_SIZE) {
                MimeType mimeType = getMimeType(new String(readUntilSymbol(CR, inputStream), UTF_8.getCharset()));

                // application/octet-stream이거나 filename이 비어있다면 스킵
                if (mimeType == MimeType.APPLICATION_OCTET_STREAM || contentDispositionValues.get("filename")
                        .isEmpty()) {
                    inputStream.skip(4);
                    continue;
                }
                // filename에서 확장자를 가져와서 mime type을 찾는다.
                String filename = contentDispositionValues.get("filename");
                inputStream.skip(2);

                byte[] multipartBytes = readUntilSymbol((CRLF + splitBoundary).getBytes(UTF_8.getCharset()),
                        inputStream);
                log.debug("read multipart file byte = {}", multipartBytes.length);

                multipartFile = new MultipartFile(filename, StringUtils.getFilenameExtension(filename), multipartBytes,
                        mimeType);
            }
        }
    }

    private String getBoundarySymbol(final List<String> mimeTypeValues) {
        return mimeTypeValues.get(1).split("=")[1];
    }

    private byte[] readUntilSymbol(final byte symbol, final InputStream inputStream) throws IOException {
        byte[] bytes;
        inputStream.mark(0);
        int count = countLine(inputStream, symbol);
        inputStream.reset();

        bytes = new byte[count];
        inputStream.read(bytes);
        skipByte(inputStream, 2);

        return bytes;
    }

    private Map<String, String> getContentDispositions(final String header) {
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

    private MimeType getMimeType(final String contentAndMimeType) {
        String[] contentAndMimeTypes = contentAndMimeType.split(":");

        return MimeType.findMimeType(contentAndMimeTypes[1].trim());
    }

    private byte[] readUntilSymbol(final byte[] bytes, final ByteArrayInputStream inputStream) {
        List<Byte> byteStore = new ArrayList<>();
        inputStream.mark(0);
        while (!isEndOfBoundary(byteStore, bytes)) {
            byteStore.add((byte) inputStream.read());
        }
        List<Byte> findBytes = byteStore.subList(0, byteStore.size() - bytes.length);
        inputStream.reset();
        inputStream.skip(findBytes.size());
        inputStream.skip(2);

        return toArray(findBytes);
    }

    private byte[] toArray(final List<Byte> bytes) {
        byte[] convertedBytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            convertedBytes[i] = bytes.get(i);
        }

        return convertedBytes;
    }

    private boolean isEndOfBoundary(final List<Byte> byteStore, final byte[] bytes) {
        if (byteStore.size() < bytes.length) {
            return false;
        }
        List<Byte> compareBytes = byteStore.subList(byteStore.size() - bytes.length, byteStore.size());
        return IntStream.range(0, compareBytes.size())
                .allMatch(i -> compareBytes.get(i) == bytes[i]);
    }

    private int countLine(final InputStream inputStream, final byte specificByte) throws IOException {
        int count = 0;
        int value;
        while ((value = inputStream.read()) != specificByte) {
            if (value == -1) {
                break;
            }
            count++;
        }
        return count;
    }

    private void skipByte(final InputStream inputStream, int count) throws IOException {
        inputStream.skip(count);
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }
}
