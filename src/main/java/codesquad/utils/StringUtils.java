package codesquad.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int periodIndex = path.lastIndexOf('.');
        if (periodIndex == -1) {
            return null;
        }
        return path.substring(periodIndex + 1);
    }
}
