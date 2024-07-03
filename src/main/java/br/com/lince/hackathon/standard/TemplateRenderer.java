package br.com.lince.hackathon.standard;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TemplateRenderer<T> {
    private static final Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates"));

    public TemplateRenderer(String template, HttpServletResponse response) {
        this.template = template;
        this.response = response;
    }

    private final String template;
    private final HttpServletResponse response;

    public void render(T params) throws IOException {
        final var templateObject = handlebars.compile(template);
        response.setContentType("text/html");
        response.getWriter().write(templateObject.apply(params));
    }
}
