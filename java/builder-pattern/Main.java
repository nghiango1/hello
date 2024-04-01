import org.HTMLElementBuilder;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;

public class Main {
    private static String defaultDocument;

    public static void main(String[] args) throws Exception {
        // image using this `javax.swing.text.html.HTML` to generate some text
        // HTML document;
        HTMLElementBuilder _HTMLBuilder = new HTMLElementBuilder();
        String document = _HTMLBuilder
                .p("Hello world")
                .a("Home", "/home")
                .build();
        System.out.printf("Document content:\n%s\n\n", document.toString());
        // Is this complex?
        document = new HTMLElementBuilder()
                .div(
                        "fix t-4 m-auto",
                        new HTMLElementBuilder()
                                .h1("Example page")
                                .a("Home", "/home")
                                .build())
                .div(
                        "m-4",
                        new HTMLElementBuilder()
                                .p("Lorem ipsum dolor sit amet, qui minim labore adipisicing minim sint cillum sint consectetur cupidatat.")
                                .build())
                .build();
        System.out.printf("Document content:\n%s\n", document.toString());

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        defaultDocument = document;
        server.createContext("/", new DefaultHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class DefaultHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, defaultDocument.length());
            OutputStream os = t.getResponseBody();
            os.write(defaultDocument.getBytes());
            os.close();
        }
    }
}
