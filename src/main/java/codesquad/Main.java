package codesquad;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        log.debug("Listening for connection on port 8080 ....");

        ExecutorService executor = Executors.newFixedThreadPool(10);

        while (true) {
            executor.execute(new ConnectionHandler(serverSocket.accept()));
        }
    }
}
