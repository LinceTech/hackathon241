package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/locacao/*")
public class LocacaoesServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(LocacaoesServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        System.out.println("request == "+request);
        System.out.println("requestPath == "+requestPath);

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                System.out.println("Cadastrar");
                loadFormEditGerentes(request, response);
                break;

            case "/cancel":
                loadFullPage(request, response);
                break;

            case "/delete":
                deleteGerentes(request, response);
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
            insertOrUpdateGerentes(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de gerentes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacoesViewData>("cadastros/locacoes/pageLocacoes", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastros/locacoes/pageLocacoes"), 0);

        JDBIConnection.instance().withExtension(LocacoesRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locacaos = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new LocacoesViewData(locacaos, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar uma locacao, e retorna página atualizada
     */
    private void insertOrUpdateGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacoesViewData>("cadastros/locacoes/pageLocacoes", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastros/locacoes/pageLocacoes"), 0);

        final var id = NumberUtils.toInt(request.getParameter("id"), 0);
        final var cliente = NumberUtils.toInt(request.getParameter("cliente"), 0);
        final var gerenteResponsavel = NumberUtils.toInt(request.getParameter("gerenteResponsavel"), 0);
        final var veiculo = NumberUtils.toInt(request.getParameter("veiculo"), 0);
        final String dataInicio = request.getParameter("dataInicio");
        final String dataEntregaVeiculo = request.getParameter("dataEntregaVeiculo");
        final float valorDiaria = NumberUtils.toFloat(request.getParameter("valorDiaria"), 0);
        final float percentualComissaoGerente = NumberUtils.toFloat(request.getParameter("percentualComissaoGerente"), 0);
        final float valorTotalPago = NumberUtils.toFloat(request.getParameter("valorTotalPago"), 0);
        final String dataPagamento = request.getParameter("dataPagamento");


        final var locacao = new Locacao(id, cliente, gerenteResponsavel, veiculo, Date.valueOf(dataInicio), Date.valueOf(dataEntregaVeiculo), valorDiaria, percentualComissaoGerente, valorTotalPago,Date.valueOf(dataPagamento));
        final var errors = new HashMap<String, String>();
//
//        if (cliente.isBlank()) {
//            errors.put("nomeError", "Nome não pode ser vazio");
//        } else if (.length() > 255) {
//            errors.put("nomeError", "Nome não pode ser maior que 255 caracteres");
//        }

        JDBIConnection.instance().withExtension(LocacoesRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(id)) {
                    dao.update(locacao);
                } else {
                    dao.insert(locacao);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locacaos = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new LocacoesViewData(locacaos, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new LocacoesViewData(errors, locacao, locacaos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de locacoes
     */
    private void loadFormEditGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Locacao>("cadastros/locacoes/formsLocacoes", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        System.out.println("id" + id);

        JDBIConnection.instance().withExtension(LocacoesRepository.class, dao -> {
            renderer.render(dao.findById(id));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(LocacoesRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }

}
