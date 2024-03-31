import org.HTMLBuilder;

public class Main {
  public static void main(String[] args) {
    // image using this `javax.swing.text.html.HTML` to generate some text
    // HTML document;
    HTMLBuilder _HTMLBuilder = new HTMLBuilder();
    String document = _HTMLBuilder
        .p("Hello world")
        .a("Home", "/home")
        .build();
    System.out.printf("Document content:\n%s\n", document.toString());
    // Is this complex?
    document = new HTMLBuilder()
        .div(
            "m-4",
            new HTMLBuilder()
                .p("Hello world")
                .p("Lorem ipsum dolor sit amet, officia excepteur ex fugiat reprehenderit enim labore culpa sint ad nisi Lorem pariatur mollit ex esse exercitation amet. Nisi anim cupidatat excepteur officia. Reprehenderit nostrud nostrud ipsum Lorem est aliquip amet voluptate voluptate dolor minim nulla est proident. Nostrud officia pariatur ut officia. Sit irure elit esse ea nulla sunt ex occaecat reprehenderit commodo officia dolor Lorem duis laboris cupidatat officia voluptate. Culpa proident adipisicing id nulla nisi laboris ex in Lorem sunt duis officia eiusmod. Aliqua reprehenderit commodo ex non excepteur duis sunt velit enim. Voluptate laboris sint cupidatat ullamco ut ea consectetur et est culpa et culpa duis.")
                .a("Home", "/home")
                .build())
        .build();
    System.out.printf("Document content:\n%s\n", document.toString());
  }
}
