package codesquad.web.handler;

import codesquad.utils.StringUtils;
import codesquad.was.AbstractRequestHandler;
import codesquad.was.http.HttpRequest;
import codesquad.was.http.HttpResponse;
import codesquad.was.http.type.MimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRequestHandler extends AbstractRequestHandler {

    public static final String UPLOAD_PATH = "./upload-image";

    private final Logger log = LoggerFactory.getLogger(ImageRequestHandler.class);

    @Override
    public void handleGet(final HttpRequest request, final HttpResponse response) throws IOException {
        String imageName = request.getParameter("imageName");
        if (Objects.isNull(imageName)) {
            log.debug("이미지를 찾을 수 없습니다.");
            return;
        }
        File imageFile = new File(UPLOAD_PATH + "/", imageName);
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] bytes = fileInputStream.readAllBytes();
        String extension = StringUtils.getFilenameExtension(imageName);

        response.dynamicForward(bytes, MimeType.findMimeType(extension));
    }
}
