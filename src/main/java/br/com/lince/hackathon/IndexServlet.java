package br.com.lince.hackathon;

import br.com.lince.hackathon.standard.TemplateRenderer;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<>("index", response);
        final var params = new HashMap<String, Object>(Map.of("message", "Olá mundo..."));

        renderer.render(params);
    }
}