package codesquad;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new Server(8080, Runtime.getRuntime().availableProcessors() * 2);
        server.start();
    }
}
