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
    private static final int PAGE_SIZE = 15;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadListaGerentes("gerentes/gerentesLista", request, response);
                break;
            case "/delete":
                excluiGerente(request, response);
                break;
            case "/ordenar":
                ordenarGerente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void ordenarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/gerentesListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0)-1;
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var gerenteFiltro = new GerenteFiltro(nome, cpf, cidade, estado);
        final var campo = request.getParameter("campo");
        final var sentido = request.getParameter("sentido");


        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var count = dao.countFilter(gerenteFiltro);
            final var gerentes = dao.selectFilterPage(page, PAGE_SIZE, gerenteFiltro, campo, sentido);

            renderer.render(new GerentesViewData(gerentes, page, PAGE_SIZE, count, gerenteFiltro, campo, sentido));
            return null;
        });
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            loadListaGerentes("gerentes/gerentesLista", request, response);
        } else if (requestPath.equals("/filtro")) {
            filtrarGerente(request, response);
        } else if (requestPath.equals("/page")) {
            loadListaGerentes("gerentes/gerentesListaCorpo", request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void filtrarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/gerentesListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var gerenteFiltro = new GerenteFiltro(nome, cpf, cidade, estado);
        

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var count = dao.countFilter(gerenteFiltro);
            final var gerentes = dao.selectFilterPage(page, PAGE_SIZE, gerenteFiltro, "nome", "ASC");

            renderer.render(new GerentesViewData(gerentes, page, PAGE_SIZE, count, gerenteFiltro));
            return null;
        });
    }

    private void excluiGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            dao.delete(id);
            loadListaGerentes("gerentes/gerentesLista", request, response);
            return null;
        });
    }


    private void loadListaGerentes(String template, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>(template, response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var gerenteFiltro = new GerenteFiltro(nome, cpf, cidade, estado);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var count = dao.countFilter(gerenteFiltro);
            final var gerentes = dao.selectFilterPage(page, PAGE_SIZE, gerenteFiltro, "nome", "ASC");

            renderer.render(new GerentesViewData(gerentes, page, PAGE_SIZE, count, gerenteFiltro));
            return null;
        });
    }
}
