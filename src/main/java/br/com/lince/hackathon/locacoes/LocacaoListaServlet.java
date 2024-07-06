package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/locacaoLista/*")
public class LocacaoListaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LocacaoListaServlet.class.getName());
    private static final int PAGE_SIZE = 15;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadListaLocacoes("locacao/locacaoLista", request, response);
                break;
            case "/delete":
                excluiLocacao(request, response);
                break;
            case "/ordenar":
                ordenarLocacao(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void ordenarLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacoes/LocacoesListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0)-1;
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var LocacaoFiltro = new LocacaoFiltro(nome, cpf, cidade, estado);
        final var campo = request.getParameter("campo");
        final var sentido = request.getParameter("sentido");


        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            final var count = dao.countFilter(LocacaoFiltro);
//            final var Locacoes = dao.selectFilterPage(page, PAGE_SIZE, LocacaoFiltro, campo, sentido);

//            renderer.render(new LocacoesViewData(Locacoes, page, PAGE_SIZE, count, LocacaoFiltro, campo, sentido));
            return null;
        });
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            loadListaLocacoes("Locacoes/LocacoesLista", request, response);
        } else if (requestPath.equals("/filtro")) {
            filtrarLocacao(request, response);
        } else if (requestPath.equals("/page")) {
            loadListaLocacoes("Locacoes/LocacoesListaCorpo", request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void filtrarLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("Locacoes/LocacoesListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var LocacaoFiltro = new LocacaoFiltro(nome, cpf, cidade, estado);
        

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            final var count = dao.countFilter(LocacaoFiltro);
//            final var Locacoes = dao.selectFilterPage(page, PAGE_SIZE, LocacaoFiltro, "nome", "ASC");

//            renderer.render(new LocacoesViewData(Locacoes, page, PAGE_SIZE, count, LocacaoFiltro));
            return null;
        });
    }

    private void excluiLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            dao.delete(id);
            loadListaLocacoes("Locacoes/LocacoesLista", request, response);
            return null;
        });
    }


    private void loadListaLocacoes(String template, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>(template, response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var placa = request.getParameter("placa");
        final var cliente = request.getParameter("cliente");
        final var gerente = request.getParameter("gerente");
        var situacao = request.getParameter("situacao");
        situacao = situacao.equals("1") ? "=" : "<>";
        final var LocacaoFiltro = new LocacaoFiltro(placa, cliente, gerente, situacao);

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            final var count = dao.countFilter(LocacaoFiltro);
            final var locacoes = dao.selectFilterPage(page, PAGE_SIZE, LocacaoFiltro, "id", "ASC");

            renderer.render(new LocacaoViewData(locacoes, page, PAGE_SIZE, count, LocacaoFiltro));
            return null;
        });
    }
}
