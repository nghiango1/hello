package org;

/**
 * Builder is a helper class to create HTML
 */
public class HTMLBuilder {
    private String elementContent;

    public HTMLBuilder() {
        this.elementContent = "";
    }

    public HTMLBuilder(String content) {
        this.elementContent = content;
    }

    public HTMLBuilder p(String paragrapContent) {
        this.elementContent = String.format("%s<p>%s</p>", this.elementContent, paragrapContent);
        return this;
    }

    public HTMLBuilder a(String hyperlinkContent, String uri) {
        this.elementContent = String.format("%s<a href=\"%s\">%s</a>", this.elementContent, uri, hyperlinkContent);
        return this;
    }

    public HTMLBuilder div(String divClass, String divContent) {
        this.elementContent = String.format("%s<div class=\"%s\">%s</div>", this.elementContent, divClass, divContent);
        return this;
    }

    public String build() {
        return this.elementContent;
    }
}
