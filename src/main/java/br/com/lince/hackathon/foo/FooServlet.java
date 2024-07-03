package br.com.lince.hackathon.foo;

import br.com.lince.hackathon.standard.JDBIConnection;
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
@WebServlet("/foo/*")
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
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
        logger.info("responder get [ " + requestPath + " ]");

        switch (requestPath) {
            case "":
                loadFullPage(request, response);
                break;

            case "/upsert":
                manageFoo(request, response);
                break;

            case "/delete":
                deleteFoo(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var foos = dao.selectPage(0, 25);

            fooRender.render(new FooViewData(foos, now));

            return null;
        });
    }

    private void manageFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);

        final var bar = Integer.parseInt(request.getParameter("bar"));
        final var bas = request.getParameter("bas");
        final var boo = request.getParameter("boo");
        final var foo = new Foo(bar, bas, boo);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            if (dao.exists(bar)) {
                dao.update(foo);
            } else {
                dao.insert(foo);
            }
            return null;
        });
    }

    private void deleteFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);
        final var bar = Integer.parseInt(request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            dao.delete(bar);
            return null;
        });
    }
}
