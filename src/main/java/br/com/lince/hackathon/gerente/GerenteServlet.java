package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.utils.Validacao;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;
import org.jdbi.v3.core.extension.ExtensionCallback;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import static br.com.lince.hackathon.utils.Validacao.isCpf;
import static br.com.lince.hackathon.utils.Validacao.isMaior18;

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de gerentes
 */
@WebServlet("/gerente/*")
public class GerenteServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(GerenteServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = isPathNull(request);
        System.out.println("requestPath >>> "+requestPath);
        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;
            case "/edit":
                loadFormEditGerente(request, response);
                break;
            case "/delete":
                deleteGerente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = isPathNull(request);

        if (requestPath.isBlank()) {
            loadFullPage(request, response);
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateGerente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de gerentes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page     = NumberUtils.toInt(request.getParameter("page"), 0);
        final var pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 15);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, pageSize);

            renderer.render(new GerenteViewData(gerentes, now, page, pageSize, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um gerente, e retorna página atualizada
     */
    private void insertOrUpdateGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var nr_cpf = request.getParameter("nr_cpf");
        final var nm_gerente = request.getParameter("nm_gerente");
        final var ds_email = request.getParameter("ds_email");
        final var nm_cidade = request.getParameter("nm_cidade");
        final var nm_estado = request.getParameter("nm_estado");
        final var nr_telefone = NumberUtils.toLong(request.getParameter("nr_telefone"), 0);
        final var dt_contratacao = LocalDate.parse(request.getParameter("dt_contratacao"), DateTimeFormatter.ISO_DATE);
        final var dt_nascimento = LocalDate.parse(request.getParameter("dt_nascimento"), DateTimeFormatter.ISO_DATE);
        final var pc_comissao = NumberUtils.toDouble(request.getParameter("pc_comissao"), 0.0);
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 15);
        final var errors = new HashMap<String, String>();

        if (!Validacao.isCpf(nr_cpf)) {
            errors.put("cpfError", "CPF inválido!");
        }

        //Email é validado a partir do field email do HTML

        if (!Validacao.isMaior18(dt_nascimento)) {
            errors.put("dt_nascimentoError", "A idade não pode ser inferior a 18 anos!");
        }

        //Campos obrigatórios via required no HTML

        if (pc_comissao > 25.0) {
            errors.put("pc_comissaoError", "O percentual de comissão não pode ser superior a 25%");
        }

        final var gerente = new Gerente(nr_cpf, nm_gerente, nr_telefone, ds_email, nm_cidade, nm_estado, pc_comissao, dt_contratacao, dt_nascimento);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                System.out.println("Linha 124");
                if (dao.exists(gerente.getNr_cpf())) {
                    System.out.println("Linha 126");
                    dao.update(gerente);
                } else {
                    System.out.println("Linha 129");
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, pageSize);

            if (errors.isEmpty()) {
                renderer.render(new GerenteViewData(gerentes, now, page, pageSize, count));
            } else {
                renderer.render(new GerenteViewData(errors, gerente, gerentes, now, page, pageSize, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de gerentes
     */
    private void loadFormEditGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerente>("gerente/form", response);
        final var cpf = request.getParameter("nr_cpf");

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            renderer.render(dao.findByCpf(cpf));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de gerentes
     */
    private void deleteGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var nr_cpf = request.getParameter("nr_cpf");
        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            dao.delete(nr_cpf);
            loadFullPage(request, response);
            return null;
        });
    }

    private String isPathNull(HttpServletRequest request) {
        if (!isNull(request.getPathInfo())) {
            return request.getPathInfo();
        }

        return "";
    }

    private boolean isNull(Object obj){
        return Objects.isNull(obj);
    }


}
