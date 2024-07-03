package br.com.lince.hackathon;

import com.github.jknack.handlebars.Handlebars;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(IndexServlet.class.getName());

    @Override
    public void init() {
        logger.info("[indexServlet] servlet inicializado");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Responder requisição [olá mundo]");

        final var handlebars = new Handlebars();
        final var template = handlebars.compile("templates/index");
        final var params = new HashMap<String, Object>(Map.of("message", "Olá mundo..."));
        final var html = template.apply(params);

        response.setContentType("text/html");
        response.getWriter().write(html);
    }

    @Override
    public void destroy() {
        logger.info("[indexServlet] servlet finalizado");
    }
}