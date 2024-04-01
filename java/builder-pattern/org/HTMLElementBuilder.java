package org;

/**
 * Builder is a helper class to create HTML
 */
public class HTMLElementBuilder {
    private String elementContent;

    public HTMLElementBuilder() {
        this.elementContent = "";
    }

    public HTMLElementBuilder(String content) {
        this.elementContent = content;
    }

    public HTMLElementBuilder h1(String paragrapContent) {
        this.elementContent = String.format("%s<h1>%s</h1>", this.elementContent, paragrapContent);
        return this;
    }

    public HTMLElementBuilder p(String paragrapContent) {
        this.elementContent = String.format("%s<p>%s</p>", this.elementContent, paragrapContent);
        return this;
    }

    public HTMLElementBuilder a(String hyperlinkContent, String uri) {
        this.elementContent = String.format("%s<a href=\"%s\">%s</a>", this.elementContent, uri, hyperlinkContent);
        return this;
    }

    public HTMLElementBuilder div(String divClass, String divContent) {
        this.elementContent = String.format("%s<div class=\"%s\">%s</div>", this.elementContent, divClass, divContent);
        return this;
    }

    public String build() {
        return this.elementContent;
    }
}
