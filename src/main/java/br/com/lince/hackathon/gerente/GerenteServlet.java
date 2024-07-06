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
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static br.com.lince.hackathon.utils.Validacao.isCpf;
import static br.com.lince.hackathon.utils.Validacao.isMaior18;

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de gerentes
 */
@WebServlet("/gerente/*")
public class GerenteServlet extends HttpServlet {
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
        final var nr_cpf = request.getParameter("nr_cpf").replace(".", "").replace("-", "").trim();
        final var nm_gerente = request.getParameter("nm_gerente");
        final var ds_email = request.getParameter("ds_email");
        final var nm_cidade = request.getParameter("nm_cidade");
        final var nm_estado = request.getParameter("nm_estado");
        final var nr_telefone = NumberUtils.toLong(request.getParameter("nr_telefone").replace("(", "").replace(")", "").replace("-", "").trim(), 0);
        final var pc_comissao = NumberUtils.toDouble(request.getParameter("pc_comissao"), 0.0);
        final Map<String, Object> errors = new HashMap<>();

        if (nr_cpf.isBlank()) {
            errors.put("nr_cpfError", "CPF deve ser preenchido!");
        }
        if (nm_gerente.isBlank()) {
            errors.put("nm_gerenteError", "Nome deve ser preenchido!");
        }
        if (ds_email.isEmpty()) {
            errors.put("ds_emailError", "E-mail deve ser preenchido!");
        }
        if (nm_cidade.isEmpty()) {
            errors.put("nm_cidadeError", "Nome da cidade deve ser preenchido!");
        }
        if (nm_estado.isEmpty()) {
            errors.put("nm_estadoError", "Nome do estado deve ser preenchido!");
        }
        if (nr_telefone == 0) {
            errors.put("nr_telefoneError", "Nº de telefone deve ser preenchido!");
        }
        if (pc_comissao == 0) {
            errors.put("pc_comissaoError", "% de comissão deve ser preenchida!");
        }


        LocalDate dt_contratacao = null;
        try {
            dt_contratacao = LocalDate.parse(request.getParameter("dt_contratacao"), DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            errors.put("dt_contratacaoError", "Data contratação inválido!");
        }

        LocalDate dt_nascimento = null;
        try {
            dt_nascimento = LocalDate.parse(request.getParameter("dt_nascimento"), DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            errors.put("dt_nascimentoError", "Data nascimento inválido!");
        }

        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 15);

        if (!Validacao.isCpf(nr_cpf)) {
            errors.put("nr_cpfError", "CPF inválido!");
        }

        if (!Validacao.isEmail(ds_email)) {
            errors.put("ds_emailError", "E-mail inválido!");
        }

        //Email é validado a partir do field email do HTML

        try {
            if (!Validacao.isMaior18(dt_nascimento)) {
                errors.put("dt_nascimentoError", "A idade não pode ser inferior a 18 anos!");
            }
        } catch (NullPointerException e) {
            errors.put("dt_nascimentoError", "Necessário preencher a data de nascimento");
        }

        //Campos obrigatórios via required no HTML

        if (pc_comissao > 25.0) {
            errors.put("pc_comissaoError", "O percentual de comissão não pode ser superior a 25%");
        }

        final var gerente = new Gerente(nr_cpf, nm_gerente, nr_telefone, ds_email, nm_cidade, nm_estado, pc_comissao, dt_contratacao, dt_nascimento);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(gerente.getNr_cpf())) {
                    dao.update(gerente);
                } else {
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, pageSize);

            if (errors.isEmpty()) {
                renderer.render(new GerenteViewData(gerentes, now, page, pageSize, count));
            } else {
                renderer.render(new GerenteViewData((HashMap) errors, gerente, gerentes, now, page, pageSize, count));
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
        if (!Validacao.isNull(request.getPathInfo())) {
            return request.getPathInfo();
        }

        return "";
    }
}
