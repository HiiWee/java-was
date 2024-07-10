package codesquad.was.http.type;

public enum CharsetType {

    UTF_8("UTF-8");

    private final String charset;

    CharsetType(final String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}
