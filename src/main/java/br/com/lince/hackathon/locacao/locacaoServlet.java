package br.com.lince.hackathon.locacao;

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

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de foos
 */
@WebServlet("/locacao/*")
public class locacaoServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(locacaoServlet.class.getName());

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
                deleteLocacao(request, response);
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
            insertOrUpdateLocacao(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<locacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        var orderBy = "CDLOCACAO:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDLOCACAO:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        JDBIConnection.instance().withExtension(locacaoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locacoes = dao.selectPage(page, PAGE_SIZE, odb);

            renderer.render(new locacaoViewData(locacoes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<locacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        var orderBy = "CDLOCACAO:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDLOCACAO:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        final var cdlocacao = NumberUtils.toInt(request.getParameter("CDLOCACAO"), 0);
        final var cdcliente = NumberUtils.toInt(request.getParameter("CDCLIENTE"), 0);
        final var cdgerente = NumberUtils.toInt(request.getParameter("CDGERENTE"), 0);
        final var cdveiculo = NumberUtils.toInt(request.getParameter("CDVEICULO"), 0);
        final var dtinicio = LocalDate.parse(request.getParameter("DTINICIO"), DateTimeFormatter.ISO_LOCAL_DATE);
        final var dtentrega = LocalDate.parse(request.getParameter("DTENTREGA"), DateTimeFormatter.ISO_LOCAL_DATE);
        final var valdiaria = NumberUtils.toDouble(request.getParameter("VALDIARIA"),0.0);
        final var pccomissao = NumberUtils.toDouble(request.getParameter("PCCOMISSAO"),0.0);
        final var valpago = NumberUtils.toDouble(request.getParameter("VALPAGO"),0.0);
        final var datapagamento = LocalDate.parse(request.getParameter("DATAPAGAMENTO"), DateTimeFormatter.ISO_LOCAL_DATE);
        final var locacao = new Locacao(cdlocacao, cdcliente, cdgerente, cdveiculo, dtinicio, dtentrega, valdiaria, pccomissao, valpago,datapagamento);
        final var errors = new HashMap<String, String>();

        if (cdlocacao == 0) {
            errors.put("Código do Locacao", "Não pode ser vazio");
        } else if (cdlocacao > 999999999) {
            errors.put("Código do Locacao", "Não pode ser maior que 9 caracteres");
        }

        if (cdcliente == 0) {
            errors.put("Código do Cliente", "Não pode ser vazio");
        } else if (cdcliente > 999999999) {
            errors.put("Código do Cliente", "Não pode ser maior que 9 caracteres");
        }

        if (cdgerente == 0) {
            errors.put("Código do Gerente", "Não pode ser vazio");
        } else if (cdgerente > 999999999) {
            errors.put("Código do Gerente", "Não pode ser maior que 9 caracteres");
        }

        if (cdveiculo == 0) {
            errors.put("Código do Veiculo", "Não pode ser vazio");
        } else if (cdveiculo > 999999999) {
            errors.put("Código do Veiculo", "Não pode ser maior que 9 caracteres");
        }

        if (dtinicio == null) {
            errors.put("Data de inicio", "Não pode ser vazio");
        }

        if (dtentrega == null) {
            errors.put("Data de entrega", "Não pode ser vazio");
        }

        if (datapagamento == null) {
            errors.put("Data de pagamento", "Não pode ser vazio");
        }

        if (valdiaria == 0) {
            errors.put("Valor da diária", "Não pode ser vazio");
        } else if (valdiaria > 999999999) {
            errors.put("Valor da diária", "Não pode ser maior que 9 caracteres");
        }

        if (pccomissao == 0) {
            errors.put("Percentual comissao", "Não pode ser vazio");
        } else if (pccomissao > 999999999) {
            errors.put("Percentual comissao", "Não pode ser maior que 9 caracteres");
        }

        log(errors.toString());
        JDBIConnection.instance().withExtension(locacaoRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cdlocacao)) {
                    dao.update(locacao);
                } else {
                    dao.insert(locacao);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var veiculos = dao.selectPage(page, PAGE_SIZE, odb);

            if (errors.isEmpty()) {
                renderer.render(new locacaoViewData(veiculos, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new locacaoViewData(errors, locacao, veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Locacao>("locacao/form", response);
        final var locacao = NumberUtils.toInt(request.getParameter("CDLOCACAO"), 0);

        JDBIConnection.instance().withExtension(locacaoRepository.class, dao -> {
            renderer.render(dao.findByBar(locacao));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log(request.getParameter("CDLOCACAO"));

        final var locacao = Integer.parseInt(request.getParameter("CDLOCACAO"));

        JDBIConnection.instance().withExtension(locacaoRepository.class, dao -> {
            dao.delete(locacao);
            loadFullPage(request, response);
            return null;
        });
    }


}
