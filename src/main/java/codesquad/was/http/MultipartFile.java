package codesquad.was.http;

import codesquad.was.http.type.MimeType;
import java.util.UUID;

public class MultipartFile {

    private final String originalFilename;
    private final String extension;
    private final byte[] bytes;
    private final MimeType mimeType;

    public MultipartFile(final String originalFilename,
                         final String extension,
                         final byte[] bytes,
                         final MimeType mimeType) {
        this.originalFilename = originalFilename;
        this.extension = extension;
        this.bytes = bytes;
        this.mimeType = mimeType;
    }

    public String randomName() {
        return UUID.randomUUID() + "." + extension;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "MultipartFile{" +
                "originalFilename='" + originalFilename + '\'' +
                ", extension='" + extension + '\'' +
                ", bytesLength=" + bytes.length +
                ", mimeType=" + mimeType +
                '}';
    }
}
