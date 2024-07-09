package br.com.lince.hackathon.veiculos;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/veiculosLista/*")
public class VeiculosListaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VeiculosListaServlet.class.getName());
    private static final int PAGE_SIZE = 15;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadListaVeiculo("veiculos/veiculosLista", request, response);
                break;
            case "/delete":
                excluiVeiculo(request, response);
                break;
            case "/ordenar":
                ordenarVeiculo(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void ordenarVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("veiculos/veiculosListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0)-1;
        final var marca = request.getParameter("marca");
        final var modelo = request.getParameter("modelo");
        final var anoDeFabricacao = request.getParameter("anoDeFabricacao");
        final var cor = request.getParameter("cor");
        final var tipoDeCombustivel = request.getParameter("tipoDeCombustivel");
        final var placa = request.getParameter("placa");
        final var veiculoFiltro = new VeiculoFiltro(marca, modelo, anoDeFabricacao, cor, placa, tipoDeCombustivel);
        final var campo = request.getParameter("campo");
        final var sentido = request.getParameter("sentido");
        setarFlags(veiculoFiltro);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var count = dao.countFilter(veiculoFiltro);
            final var gerentes = dao.selectFilterPage(page, PAGE_SIZE, veiculoFiltro, campo, sentido);

            renderer.render(new VeiculosViewData(gerentes, page, PAGE_SIZE, count, veiculoFiltro, campo, sentido));
            return null;
        });
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            loadListaVeiculo("veiculos/veiculosLista", request, response);
        } else if (requestPath.equals("/filtro")) {
            filtrarVeiculo(request, response);
        } else if (requestPath.equals("/page")) {
            loadListaVeiculo("veiculos/veiculosListaCorpo", request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void filtrarVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("veiculos/veiculosListaCorpo", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var marca = request.getParameter("marca");
        final var modelo = request.getParameter("modelo");
        final var anoDeFabricacao = request.getParameter("anoDeFabricacao");
        final var cor = request.getParameter("cor");
        final var tipoDeCombustivel = request.getParameter("tipoDeCombustivel");
        final var placa = request.getParameter("placa");
        final var veiculoFiltro = new VeiculoFiltro(marca, modelo, anoDeFabricacao, cor, placa, tipoDeCombustivel);
        setarFlags(veiculoFiltro);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var count = dao.countFilter(veiculoFiltro);
            final var gerentes = dao.selectFilterPage(page, PAGE_SIZE, veiculoFiltro, "id", "ASC");

            renderer.render(new VeiculosViewData(gerentes, page, PAGE_SIZE, count, veiculoFiltro));
            return null;
        });
    }

    private void excluiVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final int id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            dao.delete(id);
            loadListaVeiculo("veiculos/veiculosLista", request, response);
            return null;
        });
    }


    private void loadListaVeiculo(String template, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>(template, response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var marca = request.getParameter("marca");
        final var modelo = request.getParameter("modelo");
        final var anoDeFabricacao = request.getParameter("anoDeFabricacao");
        final var cor = request.getParameter("cor");
        final var tipoDeCombustivel = request.getParameter("tipoDeCombustivel");
        final var placa = request.getParameter("placa");
        final var veiculoFiltro = new VeiculoFiltro(marca, modelo, anoDeFabricacao, cor, placa, tipoDeCombustivel);
        setarFlags(veiculoFiltro);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var count = dao.countFilter(veiculoFiltro);
            final var veiculos = dao.selectFilterPage(page, PAGE_SIZE, veiculoFiltro, "id", "ASC");

            renderer.render(new VeiculosViewData(veiculos, page, PAGE_SIZE, count, veiculoFiltro));
            return null;
        });
    }

    private void setarFlags(VeiculoFiltro vf) {
        int tp = vf.getTipoDeCombustivel().isBlank() ? 0 : Integer.parseInt(vf.getTipoDeCombustivel());
        switch (tp) {
            case 1:
                vf.fgAlcool=true;
                break;
            case 2:
                vf.fgGasolina=true;
                break;
            case 3:
                vf.fgGNV=true;
                break;
            case 4:
                vf.fgEletrico=true;
                break;
            default:
                vf.semCombustivel=true;
                break;
        }
    }
}
