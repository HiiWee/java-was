package codesquad.was;

public class ContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private ContextHolder() {
    }

    public static void setContext(String context) {
        contextHolder.set(context);
    }

    public static String getContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
