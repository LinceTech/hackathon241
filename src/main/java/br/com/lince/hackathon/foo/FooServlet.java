package br.com.lince.hackathon.foo;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
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

            case "/edit":
                editFoo(request, response);
                break;

            case "/delete":
                deleteFoo(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
        logger.info("responder post [ " + requestPath + " ]");

        if (requestPath.equals("/upsert")) {
            manageFoo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("load full page");

        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var foos = dao.selectPage(0, 25);

            fooRender.render(new FooViewData(foos, now));

            return null;
        });
    }

    private void manageFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("manage foo");
        final var fooRender = new TemplateRenderer<Foo>("fooView_edit", response);

        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);
        final var bas = request.getParameter("bas");
        final var boo = request.getParameter("boo");
        final var foo = new Foo(bar, bas, boo);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            if (dao.exists(bar)) {
                dao.update(foo);
            } else {
                dao.insert(foo);
            }
            fooRender.render(null);
            return null;
        });
    }

    private void editFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var fooRender = new TemplateRenderer<Foo>("fooView_edit", response);
        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);

        logger.info("bar: " + request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            final var foo = dao.findByBar(bar);

            fooRender.render(foo);

            return null;
        });
    }

    private void deleteFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("delete foo");

        final var fooRender = new TemplateRenderer<FooViewData>("fooView", response);
        final var bar = Integer.parseInt(request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            dao.delete(bar);
            return null;
        });
    }
}
