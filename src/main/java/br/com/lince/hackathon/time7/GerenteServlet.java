package br.com.lince.hackathon.time7;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
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
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de gerentes
 */
@WebServlet("/gerente/*")
public class GerenteServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;
    private static final int now = NumberUtils.toInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(GerenteServlet.class.getName());

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
     * Trata a requisição para retorna a página de gerentes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count("Gerente");
            final var gerentes = dao.selectPageGerente(page, PAGE_SIZE);

            renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um gerente, e retorna página atualizada
     */
    private void insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var id           = NumberUtils.toInt(request.getParameter("id"), 0);
        final var nome         = request.getParameter("nome");
        final long cpf         = NumberUtils.toLong(request.getParameter("cpf"), 0);
        final long telefone    = NumberUtils.toLong(request.getParameter("telefone"), 0);
        final var email        = "";//request.getParameter("email");
        final var cidade       = "";//request.getParameter("cidade");
        final var estado       = "";//request.getParameter("estado");
        final var comissao     = 0;// NumberUtils.toInt(request.getParameter("numero"), 0);
        final var dtContrata   = NumberUtils.toInt(request.getParameter("dtNascimento").replaceAll("-", ""), 0);

        final var gerente = new Gerente(id, nome, cpf, telefone, email, cidade, estado, comissao, dtContrata);
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("nomeError", "Não pode ser vazio");
        } else if (nome.length() > 60) {
            errors.put("nomeError", "Não pode ser maior que 60 caracteres");
        }

        if (cpf == 0) {
            errors.put("cpfError", "Não pode ser vazio");
        } else if (ValidaCPF.isCPF(String.format("%11d",cpf))) {
            errors.put("cpfError", "CPF inválido");
        }

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            // Verificar se ocorreram erros no formulário
//            if (errors.isEmpty()) {
//                if (dao.existsGerente(id) && id != 0) {
//                    dao.updateGerente(gerente);
//                } else {
//                    dao.insertGerente(gerente);
//                }
//            }

            final var now = LocalDateTime.now();
            final var count = dao.count("Gerente");
            final var gerentes = dao.selectPageGerente(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new GerenteViewData(errors, gerente, gerentes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de gerentes
     */
    private void loadFormEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerente>("gerente/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            renderer.render(dao.findByIdGerente(id));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de gerentes
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(Time7Repository.class, dao -> {
            dao.deleteGerente(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
