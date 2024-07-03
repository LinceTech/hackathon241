package br.com.lince.hackathon;

import com.github.jknack.handlebars.Handlebars;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(HelloServlet.class.getName());

    @Override
    public void init() {
        logger.info("[helloServlet] servlet inicializado");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Responder requisição [olá mundo]");

        final var handlebars = new Handlebars();
        final var template = handlebars.compile("templates/sample");
        final var params = new HashMap<String, Object>(Map.of("message", "Olá mundo..."));
        final var html = template.apply(params);

        response.setContentType("text/html");
        response.getWriter().write(html);
    }

    @Override
    public void destroy() {
        logger.info("[helloServlet] servlet finalizado");
    }
}