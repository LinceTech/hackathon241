package br.com.lince.hackathon.foo;

import br.com.lince.hackathon.standard.TemplateRenderer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de foos
 */
@WebServlet("/foo")
public class FooServlet extends HttpServlet {
    /*
     * Logger padrão do servlet
     */
    private final Logger logger = Logger.getLogger(FooServlet.class.getName());

    @Override
    public void init() {
        logger.info("servlet inicializado");
    }

    @Override
    public void destroy() {
        logger.info("servlet finalizado");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("responder get");

        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);
        final var now = LocalDateTime.now();

        fooRender.render(new FooViewData(List.of(), now));
    }
}
