package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.time7.Time7Repository;
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
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de locacoes
 */
@WebServlet("/locacao/*")
public class LocacaoServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;
    private static final int now = NumberUtils.toInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(LocacaoServlet.class.getName());

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
     * Trata a requisição para retorna a página de locacoes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count("Locacao");
            final var locacoes = dao.selectPageLocacao(page, PAGE_SIZE);

            renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um locacao, e retorna página atualizada
     */
    private void insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var id              = NumberUtils.toInt(request.getParameter("id"), 0);
        final var cliente         = NumberUtils.toInt(request.getParameter("cliente"), 0);
        final var gerente         = NumberUtils.toInt(request.getParameter("gerente"), 0);
        final var veiculo         = NumberUtils.toInt(request.getParameter("veiculo"), 0);
        final var dtInicio        = NumberUtils.toInt(request.getParameter("dtInicio").replaceAll("-", ""), 0);
        final var dtFinal         = NumberUtils.toInt(request.getParameter("dtFinal").replaceAll("-", ""), 0);
        final var custoDiaria     = NumberUtils.toFloat(request.getParameter("custoDiaria"), 0);
        final var comissaoGerente = NumberUtils.toFloat(request.getParameter("comissaoGerente"), 0);
        final var valorTotal      = NumberUtils.toFloat(request.getParameter("valorTotal"), 0);
        final var dtPagamento     = NumberUtils.toInt(request.getParameter("dtPagamento").replaceAll("-", ""), 0);

        final var locacao = new Locacao(id, cliente, gerente, veiculo, dtInicio, dtFinal, custoDiaria, comissaoGerente, valorTotal, dtPagamento);
        final var errors = new HashMap<String, String>();

        if (cliente == 0) {
            errors.put("clienteError", "Não pode ser vazio");
        }
        
        if (gerente == 0) {
            errors.put("gerenteError", "Não pode ser vazio");
        }
        
        if (veiculo == 0) {
            errors.put("veiculoError", "Não pode ser vazio");
        }

        if (dtInicio == 0) {
            errors.put("dtInicioError", "Não pode ser vazio");
        } else if (dtInicio > now) {
            errors.put("dtInicioError", "Data Inicio inválida");
        }

        if (dtFinal == 0) {
            errors.put("dtFinalError", "Não pode ser vazio");
        } else if (dtFinal > now) {
            errors.put("dtFinalError", "Data Final inválida");
        } else if (dtFinal > dtInicio) {
            errors.put("dtFinalError", "Data Final inválida");
        }

        if (custoDiaria <= 0) {
            errors.put("custoDiariaError", "Custo diária deve ser maior que zero");
        }

        if (comissaoGerente == 0) {
            errors.put("comissaoGerenteError", "Não pode ser vazio");
        } else if(comissaoGerente > 25){
            errors.put("comissaoGerenteError", "Comissão não pode ser maior que 25%");
        }

        if (valorTotal <= 0) {
            errors.put("valorTotalError", "Valor total deve ser maior que zero");
        }

        if (dtPagamento == 0) {
            errors.put("dtPagamentoError", "Não pode ser vazio");
        } else if (dtPagamento > now) {
            errors.put("dtPagamentoError", "Data de Pagamento inválida");
        }

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.existsLocacao(id) && id != 0) {
                    dao.updateLocacao(locacao);
                } else {
                    dao.insertLocacao(locacao);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count("Locacao");
            final var locacoes = dao.selectPageLocacao(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new LocacaoViewData(errors, locacao, locacoes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de locacoes
     */
    private void loadFormEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            renderer.render(new LocacaoViewData(dao.findByIdLocacao(id)));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de locacoes
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            dao.deleteLocacao(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
