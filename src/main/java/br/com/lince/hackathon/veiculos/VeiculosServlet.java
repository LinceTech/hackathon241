package br.com.lince.hackathon.veiculos;


import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/veiculos/*")
public class VeiculosServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VeiculosServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaPagina(response);
                break;
            case "/new":
            case "/edit":
                formularioVeiculo(request, response);
                break;
            case "/delete":
                deletarVeiculo(request, response);
                break;
            case "/listar":
                listarVeiculos(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "/save":
                criarOuAtualizaVeiculo(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void listarVeiculos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("/veiculos/VeiculoLista", response);

        final var marca = request.getParameter("marca").trim();
        final var modelo = request.getParameter("modelo").trim();
        final var anoFabricacao = Integer.parseInt(request.getParameter("anoFabricacao").trim());
        final var placa = request.getParameter("placa").trim();
        final var cor = request.getParameter("cor").trim();
        final var tipoCombustivel = request.getParameter("tipoCombustivel").trim();
        final var proximaPagina = Boolean.parseBoolean(request.getParameter("proximaPagina").trim());

        final var numeroPaginaReq = NumberUtils.toInt(request.getParameter("numeroPagina").trim(), 0);

        final var filtros = new VeiculoFiltros(marca, modelo, anoFabricacao, placa, cor, tipoCombustivel);

        JDBIConnection.instance().withExtension(VeiculosRepository.class, dao -> {
            int numeroPagina = numeroPaginaReq;

            if (proximaPagina) {
                numeroPagina++;
            } else {
                numeroPagina--;

                numeroPagina = numeroPagina <= 0 ? 0 : numeroPagina;
            }

            List<Veiculos> list = dao.consultaPaginacao(numeroPagina, 15, filtros);

            renderer.render(new VeiculosViewData(list, marca, modelo, anoFabricacao, placa, cor, tipoCombustivel, numeroPagina));

            return null;
        });
    }

    private void criarOuAtualizaVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var erros = new HashMap<String, String>();

        Long id = 0l;
        String marca = "";
        String modelo = "";
        String placa = "";
        String cor = "";
        double custoDiaria = 0.0;
        String descricaoPromocional = "";
        String tipoCombustivel = "";
        int anoFabricacao = 0;

        if (request.getParameter("id") != null) {
            id = NumberUtils.toLong(request.getParameter("id"), 0);
        }
        if (request.getParameter("marca") != null) {
            marca = request.getParameter("marca");
        }
        if (request.getParameter("modelo") != null) {
            modelo = request.getParameter("modelo");
        }
        if (request.getParameter("placa") != null) {
            placa = request.getParameter("placa");
        }
        if (request.getParameter("cor") != null) {
            cor = request.getParameter("cor");
        }
        if (request.getParameter("custoDiaria") != null) {
            custoDiaria = NumberUtils.toDouble(request.getParameter("custoDiaria").replace(",", "."));
        }
        if (request.getParameter("descricaoPromocional") != null) {
            descricaoPromocional = request.getParameter("descricaoPromocional");
        }
        if (request.getParameter("tipoCombustivel") != null) {
            tipoCombustivel = request.getParameter("tipoCombustivel");
        }
        if (request.getParameter("anoFabricacao") != null) {
            anoFabricacao = NumberUtils.toInt(request.getParameter("anoFabricacao"), 0);
        }

        Veiculos veiculo = new Veiculos(
                id,
                marca,
                modelo,
                placa,
                cor,
                anoFabricacao,
                custoDiaria,
                descricaoPromocional,
                tipoCombustivel
                );

        if (veiculo.getMarca().isBlank()) {
            erros.put("marcaErro", "Informe a marca");
        }
        if (veiculo.getModelo().isBlank()) {
            erros.put("modeloErro", "Informe o modelo");
        }
        if (veiculo.getPlaca().isBlank()) {
            erros.put("placaErro", "Informe a placa");
        }
        if (veiculo.getCor().isBlank()) {
            erros.put("corErro", "Informe a cor");
        }
        if (veiculo.getCustoDiaria() == 0) {
            erros.put("emailErro", "Informe o custo da diária");
        }
        if (veiculo.getDescricaoPromocional().isBlank()) {
            erros.put("descricaoPromocionalErro", "Informe a descrição promocional");
        }
        if (veiculo.getTipoCombustivel().isBlank()) {
            erros.put("tipoCombustivelErro", "Informe o tipo de combustível");
        }
        if (veiculo.getAnoFabricacao() == 0) {
            erros.put("anoFabricacaoErro", "Informe o ano de fabricação");
        }

        final Long idConsulta = id;

        JDBIConnection.instance().withExtension(VeiculosRepository.class, dao -> {
            var rendererModal = new TemplateRenderer<Veiculos>("veiculos/VeiculosModal", response);
            var renderLista = new TemplateRenderer<VeiculosViewData>("veiculos/VeiculoLista", response);


            if (erros.isEmpty()) {
                if (dao.existeVeiculo(idConsulta)) {
                    dao.atualizaVeiculo(veiculo);
                } else {
                    dao.insereVeiculo(veiculo);
                }
            }

            if (erros.isEmpty()) {
                final var list = dao.consultaPaginacao(0, 15, new VeiculoFiltros("", "", 0, "", "", ""));

                renderLista.render(new VeiculosViewData(list, "","",0,"","","", 0));
            } else {
                response.setStatus(500);
                response.setHeader("HX-Reswap", "innerHTML");

                veiculo.setErros(erros);
                rendererModal.render(veiculo);
            }

            return null;
        });
    }

    private void deletarVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("/veiculos/VeiculoLista", response);

        final var veiculoID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(VeiculosRepository.class, dao -> {
            dao.deleteVeiculo(veiculoID);

            List<Veiculos> list = dao.consultaPaginacao(0, 15, new VeiculoFiltros("", "", 0, "", "", ""));

            renderer.render(new VeiculosViewData(list, "", "", 0, "", "", "", 0));

            return null;
        });
    }

    private void formularioVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Veiculos>("/veiculos/VeiculosModal", response);

        final var veiculoID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(VeiculosRepository.class, dao -> {
            var veiculo = new Veiculos();

            if (veiculoID != 0) {
                veiculo = dao.pegaVeiculosPeloID(veiculoID);
            }

            renderer.render(veiculo);

            return null;
        });
    }

    void carregaPagina(HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("/veiculos/VeiculosPage", response);

        JDBIConnection.instance().withExtension(VeiculosRepository.class, dao -> {

            List<Veiculos> list = dao.consultaPaginacao(0, 15, new VeiculoFiltros("", "", 0, "", "", ""));

            renderer.render(new VeiculosViewData(list, "", "", 0, "", "", "", 0));

            return null;
        });
    }
}
