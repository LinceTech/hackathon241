package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/gerentesLista/*")
public class GerentesListaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GerentesListaServlet.class.getName());
    private static final int PAGE_SIZE = 20;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadListaGerentes(request, response);
                break;
            case "/delete":
                excluiGerente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void excluiGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            dao.delete(id);
            loadListaGerentes(request, response);
            return null;
        });
    }


    private void loadListaGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/gerentesLista", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new GerentesViewData(gerentes, page, PAGE_SIZE, count));
            return null;
        });
    }
}
