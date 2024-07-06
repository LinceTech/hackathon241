package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.foo.FooRepository;
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

@WebServlet("/gerentes/*")
public class GerentesServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(br.com.lince.hackathon.gerentes.GerentesServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        System.out.println("request == "+request);
        System.out.println("requestPath == "+requestPath);

        switch (requestPath) {
            case "":
                System.out.println("RequestPath vazio");
            case "/":
                loadFullPage(request, response);
                break;
            case "/cadastrar":
                carregaCadastroGerentes(request, response);
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
        final var renderer = new TemplateRenderer<GerentesViewData>("cadastros/gerentes/pageGerentes", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastros/gerentes/pageGerentes"), 0);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new GerentesViewData(gerentes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("cadastros/gerentes/pageGerentes", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastros/gerentes/pageGerentes"), 0);

        final var id = NumberUtils.toInt(request.getParameter("id"), 0);
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");
        final var telefone = request.getParameter("telefone");
        final var email = request.getParameter("email");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        float percentual_comissao = 0f;
        if (!request.getParameter("percentualComissao").isEmpty()) {
            percentual_comissao = Float.parseFloat(request.getParameter("percentualComissao"));
        }
        final String data_contratacao = request.getParameter("dataContratacao");
        final var gerente = new Gerentes(id, nome, cpf, telefone, email, cidade, estado, percentual_comissao, Date.valueOf(data_contratacao));
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("nomeError", "Nome não pode ser vazio");
        } else if (nome.length() > 255) {
            errors.put("nomeError", "Nome não pode ser maior que 255 caracteres");
        }

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(id)) {
                    dao.update(gerente);
                } else {
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new GerentesViewData(gerentes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new GerentesViewData(errors, gerente, gerentes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerentes>("cadastros/gerentes/formsGerentes", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        System.out.println("id" + id);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            renderer.render(dao.findById(id));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }

    private void carregaCadastroGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final var renderer = new TemplateRenderer<GerentesViewData>("cadastros/gerentes/formsGerentes", response);
//        final var page = NumberUtils.toInt(request.getParameter("cadastro/pageGerentes"), 0);
        renderer.render(new GerentesViewData(null, null, 0, PAGE_SIZE, 0));
        //JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {

            //renderer.render(new GerentesViewData());
        //    return null;
        //});
    }

}
