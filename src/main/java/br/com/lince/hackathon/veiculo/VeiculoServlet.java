package br.com.lince.hackathon.veiculo;

import br.com.lince.hackathon.veiculo.Veiculo;
import br.com.lince.hackathon.veiculo.VeiculoRepository;
import br.com.lince.hackathon.veiculo.VeiculoServlet;
import br.com.lince.hackathon.veiculo.VeiculoViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/veiculo/*")
public class VeiculoServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

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
                loadFormEditVeiculo(request, response);
                break;

            case "/delete":
                deleteVeiculo(request, response);
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
            insertOrUpdateVeiculo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var veiculos = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        System.out.println(request.getParameter("modelo"));
        System.out.println(request.getParameter("placa"));
        System.out.println(request.getParameter("descricaoPromocional"));

        final var modelo = request.getParameter("modelo");
        final var marca = NumberUtils.toInt(request.getParameter("marca"), 0);
        final var placa = request.getParameter("placa");
        final var cor = NumberUtils.toInt(request.getParameter("cor"), 0);
        final var anoDeFabricacao = NumberUtils.toInt(request.getParameter("anoDeFabricacao"), 0);
        final var custoDeDiaria = NumberUtils.toDouble(request.getParameter("custoDeDiaria").replaceAll(",","."), 0);
        final var descricaoPromocional = request.getParameter("descricaoPromocional");
        final var tipoDeCombustivel = NumberUtils.toInt(request.getParameter("tipoDeCombustivel"), 0);

        final var veiculo = new Veiculo(marca, modelo, placa, cor, anoDeFabricacao, custoDeDiaria, descricaoPromocional, tipoDeCombustivel);
        final var errors = new HashMap<String, String>();

        if (modelo.isBlank()) {
            errors.put("modeloError", "Não pode ser vazio");
        }
        if (marca == 0 ) {
            errors.put("marcaError", "Não pode ser vazio");
        }
        if (placa.isBlank()) {
            errors.put("placaError", "Não pode ser vazio");
        }
        if (cor == 0) {
            errors.put("corError", "Não pode ser vazio");
        }
        if (anoDeFabricacao == 0) {
            errors.put("anoDeFabricacaoError", "Não pode ser vazio");
        }
        if (custoDeDiaria == 0) {
            errors.put("custoDeDiariaError", "Não pode ser vazio");
        }
        if (descricaoPromocional.isBlank()) {
            errors.put("modeloError", "Não pode ser vazio");
        }
        if (tipoDeCombustivel == 0) {
            errors.put("descricaoPromocionalError", "Não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            if (errors.isEmpty()) {
                if (dao.exists(placa)) {
                    dao.update(veiculo);
                } else {
                    dao.insert(veiculo);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var veiculos = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new VeiculoViewData(errors, veiculo, veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Veiculo>("veiculo/form", response);
        final var placa = NumberUtils.toInt(request.getParameter("placa"), 0);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            renderer.render(dao.findByPlaca(request.getParameter("placa")));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var placa = request.getParameter("placa");

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            dao.delete(placa);
            loadFullPage(request, response);
            return null;
        });
    }
}
