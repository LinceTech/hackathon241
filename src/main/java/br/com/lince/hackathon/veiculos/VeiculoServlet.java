package br.com.lince.hackathon.veiculos;

import br.com.lince.hackathon.client.ClientViewData;
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

@WebServlet("/veiculos/*")
public class VeiculoServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;

    private static final Logger logger = Logger.getLogger(VeiculoServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
        System.out.println(requestPath);
        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/cadastro":
                carregaCadastro(request, response);
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

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderMenu = new TemplateRenderer<VeiculoViewData>("menu", response);
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculos/pageVeiculos", response);

        final var comMenu = NumberUtils.toInt(request.getParameter("comMenu"), 0);
        final var page = NumberUtils.toInt(request.getParameter("pageVeiculo"), 0);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.countVeiculos();
            final var veiculos = dao.selectPageVeiculos(page, PAGE_SIZE);

            if (comMenu == 0) {
                renderMenu.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            }
            renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));

            final var tema = NumberUtils.toInt(request.getParameter("tema"), 0);
            if(tema == 1){
                final var renderTemaEscuro = new TemplateRenderer<VeiculoViewData>("temaEscuro", response);
                renderTemaEscuro.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    private void carregaCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculos/formVeiculos", response);
        final var page = NumberUtils.toInt(request.getParameter("pageVeiculo"), 0);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.countVeiculos();
            final var veiculos = dao.selectPageVeiculos(page, PAGE_SIZE);

            System.out.println("Veiculos carrega cadastro" + veiculos);

            renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));

            final var tema = NumberUtils.toInt(request.getParameter("tema"), 0);
            if(tema == 1){
                final var renderTemaEscuro = new TemplateRenderer<VeiculoViewData>("temaEscuro", response);
                renderTemaEscuro.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*ATUALIZA VALORES*/
    private void insertOrUpdateVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculoViewData>("veiculos/pageVeiculos", response);
        final var page = NumberUtils.toInt(request.getParameter("pageVeiculo"), 0);

        final var errors = new HashMap<String, String>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        final var marca = request.getParameter("marca");
        final var modelo = request.getParameter("modelo");
        final var placa = request.getParameter("placa");
        final var cor = request.getParameter("cor");
        final var descricaoPromocional = request.getParameter("descricaoPromocional");
        final var tipoCombustivel = request.getParameter("tipoCombustivel");
        int anoFabricacao = 0;
        try {
            anoFabricacao = Integer.parseInt(request.getParameter("anoFabricacao"));
        } catch (Exception e) {
            errors.put("Ano fabricacao", "Inválido");
        }
        int custoDiaria = 0;
        try {
            custoDiaria = Integer.parseInt(request.getParameter("custoDiaria"));
            System.out.println("Custo diaria: " + custoDiaria);
        } catch (Exception e) {
            errors.put("Custo da diária", "Inválido");
        }

        final var veiculo = new Veiculo(id, marca, modelo, placa, cor, anoFabricacao, custoDiaria, descricaoPromocional, tipoCombustivel);

        //Marca
        if (marca.isBlank()) {
            errors.put("Marca", "Não pode ser vazio");
        } else if (marca.length() > 255) {
            errors.put("Marca", "Não pode ser maior que 255 caracteres");
        }
        //Marca
        if (modelo.isBlank()) {
            errors.put("modelo", "Não pode ser vazio");
        } else if (modelo.length() > 255) {
            errors.put("modelo", "Não pode ser maior que 255 caracteres");
        }

        if (placa.isBlank()) {
            errors.put("Placa", "Não pode ser vazio");
        } else if (placa.length() > 255) {
            errors.put("Placa", "Não pode ser maior que 255 caracteres");
        }

        if (cor.isBlank()) {
            errors.put("Cor", "Não pode ser vazio");
        } else if (cor.length() > 255) {
            errors.put("Cor", "Não pode ser maior que 255 caracteres");
        }

        if (descricaoPromocional.isBlank()) {
            errors.put("Descricao Promocional", "Não pode ser vazio");
        } else if (descricaoPromocional.length() > 255) {
            errors.put("Descricao Promocional", "Não pode ser maior que 255 caracteres");
        }

        if (tipoCombustivel.isBlank()) {
            errors.put("Tipo combustivel", "Não pode ser vazio");
        } else if (tipoCombustivel.length() > 255) {
            errors.put("Tipo combustivel", "Não pode ser maior que 255 caracteres");
        }

        System.out.println(errors);

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {

            // Verificar se ocorreram erros no formulário
            System.out.println("dao :>>" + dao);
            System.out.println("errors :>>" + errors);
            System.out.println("veiculo.getId() :>>" + veiculo.getId());

            if (errors.isEmpty()) {
                if (dao.existsVeiculos(veiculo.getId())) {
                    dao.updateVeiculosById(veiculo);
                } else {
                    dao.insertVeiculos(veiculo);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.countVeiculos();
            final var veiculos = dao.selectPageVeiculos(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            } else {
                final var error = new TemplateRenderer<VeiculoViewData>("client/error", response);
                response.setStatus(500);
                response.setHeader("HX-Reswap", "innerHTML");

                error.render(new VeiculoViewData(errors, veiculo, veiculos, now, page, PAGE_SIZE, count));
            }

            final var tema = NumberUtils.toInt(request.getParameter("tema"), 0);
            if(tema == 1){
                final var renderTemaEscuro = new TemplateRenderer<VeiculoViewData>("temaEscuro", response);
                renderTemaEscuro.render(new VeiculoViewData(veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*EDIT COM O FORMULÁRIO*/
    private void loadFormEditVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Veiculo>("veiculos/formVeiculos", response);
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            renderer.render(dao.findVeiculoById(id));
            return null;
        });
    }

    private void deleteVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));
        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            dao.deleteVeiculoById(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
