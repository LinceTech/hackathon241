package br.com.lince.hackathon.veiculo;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.time7.Time7Repository;
import br.com.lince.hackathon.time7.ValidaPlacaCarro;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de veiculos
 */
@WebServlet("/veiculo/*")
public class VeiculoServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;
    private static final int now = NumberUtils.toInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(VeiculoServlet.class.getName());
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEdit(request, response);
                break;

            case "/delete":
                delete(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            loadFullPage(request, response);
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdate(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de veiculos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count("Veiculo");
            final var veiculos = dao.selectPageVeiculo(page, PAGE_SIZE);

            renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um veiculo, e retorna página atualizada
     */
    private void insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var id              = NumberUtils.toInt(request.getParameter("id"), 0);
        final var marca           = request.getParameter("marca");
        final var modelo          = request.getParameter("modelo");
        final var placa           = request.getParameter("placa");
        final var cor             = request.getParameter("cor");
        final var anoFabrica      = NumberUtils.toInt(request.getParameter("anoFabrica"), 0);
        final float custoDiaria   = NumberUtils.toFloat(request.getParameter("custoDiaria"), 0);
        final var descricao       = request.getParameter("descricao");
        final var tipoCombustivel = NumberUtils.toInt(request.getParameter("tipoCombustivel"), 0);

        final var veiculo = new Veiculo(id, marca, modelo, placa, cor, anoFabrica, custoDiaria, descricao, tipoCombustivel);
        final var errors = new HashMap<String, String>();

        if (marca.isBlank()) {
            errors.put("marcaError", "Não pode ser vazio");
        } else if (marca.length() > 30) {
            errors.put("marcaError", "Não pode ser maior que 30 caracteres");
        }

        if (modelo.isBlank()) {
            errors.put("modeloError", "Não pode ser vazio");
        } else if (modelo.length() > 60) {
            errors.put("modeloError", "Não pode ser maior que 60 caracteres");
        }

        if (placa.isBlank()) {
            errors.put("placaError", "Não pode ser vazio");
        } else if (!ValidaPlacaCarro.isPlacaValid(placa)) {
            errors.put("placaError", "Placa invalida");
        }

        if (anoFabrica < 1886 || anoFabrica > now / 10000) {
            errors.put("anoFabricaError", "Ano de fabricação inválido");
        }

        if (custoDiaria <= 0) {
            errors.put("custoDiariaError", "Custo diária deve ser maior que zero");
        }

        if (descricao.length() > 8000) {
            errors.put("descricaoError", "Não pode ser maior que 8000 caracteres");
        }

        if (tipoCombustivel < 1 || tipoCombustivel > 4) {
            errors.put("tipoCombustivelError", "Tipo de combustível inválido");
        }

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.existsVeiculo(id) && id != 0) {
                    dao.updateVeiculo(veiculo);
                } else {
                    dao.insertVeiculo(veiculo);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count("Veiculo");
            final var veiculos = dao.selectPageVeiculo(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new VeiculoViewData(errors, veiculo, veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de veiculos
     */
    private void loadFormEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Veiculo>("veiculo/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            renderer.render(dao.findByIdVeiculo(id));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de veiculos
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            dao.deleteVeiculo(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
