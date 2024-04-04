import org.HTMLElementBuilder;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;

public class Main {
    private static String defaultDocument;
    private static String pageTemplate = """
                    <!doctype html>
                    <html>
                    <head>
                      <meta charset="UTF-8">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0">
                      <style>
                        .m-4 {margin: 1rem;}
                        .t-4 {top: 1rem;}
                        .r-4 {right: 1rem;}
                        .fix {position: fixed;}
                      </style>
                    </head>
                    <body>
                        %s
                    </body>
                    </html>
            """;

    public static void main(String[] args) throws Exception {
        // imagine using this `javax.swing.text.html.HTML` to generate some text
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
                        "fix m-auto t-4 r-4",
                        new HTMLElementBuilder()
                                .a("Home", "/home")
                                .build())
                .div(
                        "m-4",
                        new HTMLElementBuilder()
                                .h1("Example page")
                                .p("Lorem ipsum dolor sit amet, officia excepteur ex fugiat reprehenderit enim labore culpa sint ad nisi Lorem pariatur mollit ex esse exercitation amet. Nisi anim cupidatat excepteur officia. Reprehenderit nostrud nostrud ipsum Lorem est aliquip amet voluptate voluptate dolor minim nulla est proident. Nostrud officia pariatur ut officia. Sit irure elit esse ea nulla sunt ex occaecat reprehenderit commodo officia dolor Lorem duis laboris cupidatat officia voluptate. Culpa proident adipisicing id nulla nisi laboris ex in Lorem sunt duis officia eiusmod. Aliqua reprehenderit commodo ex non excepteur duis sunt velit enim. Voluptate laboris sint cupidatat ullamco ut ea consectetur et est culpa et culpa duis.")
                                .p("Lorem ipsum dolor sit amet, officia excepteur ex fugiat reprehenderit enim labore culpa sint ad nisi Lorem pariatur mollit ex esse exercitation amet. Nisi anim cupidatat excepteur officia. Reprehenderit nostrud nostrud ipsum Lorem est aliquip amet voluptate voluptate dolor minim nulla est proident. Nostrud officia pariatur ut officia. Sit irure elit esse ea nulla sunt ex occaecat reprehenderit commodo officia dolor Lorem duis laboris cupidatat officia voluptate. Culpa proident adipisicing id nulla nisi laboris ex in Lorem sunt duis officia eiusmod. Aliqua reprehenderit commodo ex non excepteur duis sunt velit enim. Voluptate laboris sint cupidatat ullamco ut ea consectetur et est culpa et culpa duis.")
                                .p("Lorem ipsum dolor sit amet, officia excepteur ex fugiat reprehenderit enim labore culpa sint ad nisi Lorem pariatur mollit ex esse exercitation amet. Nisi anim cupidatat excepteur officia. Reprehenderit nostrud nostrud ipsum Lorem est aliquip amet voluptate voluptate dolor minim nulla est proident. Nostrud officia pariatur ut officia. Sit irure elit esse ea nulla sunt ex occaecat reprehenderit commodo officia dolor Lorem duis laboris cupidatat officia voluptate. Culpa proident adipisicing id nulla nisi laboris ex in Lorem sunt duis officia eiusmod. Aliqua reprehenderit commodo ex non excepteur duis sunt velit enim. Voluptate laboris sint cupidatat ullamco ut ea consectetur et est culpa et culpa duis.")
                                .build())
                .build();
        System.out.printf("Document content:\n%s\n\n", document.toString());

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        defaultDocument = document;
        server.createContext("/", new DefaultHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("Start HTTP server listening at: :8000");
        server.start();
    }

    static class DefaultHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String document = String.format(pageTemplate, defaultDocument);
            t.sendResponseHeaders(200, document.length());

            OutputStream os = t.getResponseBody();
            os.write(document.getBytes());
            os.close();
        }
    }
}
