package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.gerente.GerenteRepository;
import br.com.lince.hackathon.gerente.GerenteServlet;
import br.com.lince.hackathon.gerente.GerenteViewData;
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

@WebServlet("/gerente/*")
public class GerenteServlet  extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;

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
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            loadFullPage(request, response);
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateGerente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);


        final var nome = request.getParameter("nome");
        final var cpf = NumberUtils.toInt(request.getParameter("cpf"), 0);
        final var telefone = NumberUtils.toInt(request.getParameter("telefone"), 0);
        final var email = request.getParameter("email");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var percentualComissao = NumberUtils.toDouble(request.getParameter("percentualComissao").replaceAll(",","."), 0);
        final var dataContratacao = NumberUtils.toInt(request.getParameter("dataContratacao"), 0);

        System.out.println("cpf: "+ cpf);

        final var gerente = new Gerente(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao);
        final var errors = new HashMap<String, String>();


        if (nome.isBlank()) {
            errors.put("nomeError", "Não pode ser vazio");
        }
        if (cpf == 0 ) {
            errors.put("cpfError", "Não pode ser vazio");
        }
        if (telefone == 0) {
            errors.put("telefoneError", "Não pode ser vazio");
        }
        if (email.isBlank()) {
            errors.put("emailError", "Não pode ser vazio");
        }
        if (cidade.isBlank()) {
            errors.put("cidadeError", "Não pode ser vazio");
        }
        if (estado.isBlank()) {
            errors.put("estadoError", "Não pode ser vazio");
        }
        if (percentualComissao == 0) {
            errors.put("percentualComissaoError", "Não pode ser vazio");
        }
        if (dataContratacao == 0) {
            errors.put("dataContratacaoError", "Não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cpf)) {
                    dao.update(gerente);
                } else {
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new GerenteViewData(errors, gerente, gerentes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerente>("gerente/form", response);
        final var cpf = NumberUtils.toInt(request.getParameter("cpf"), 0);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            renderer.render(dao.findByCpf(cpf));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var cpf = Integer.parseInt(request.getParameter("cpf"));

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            dao.delete(cpf);
            loadFullPage(request, response);
            return null;
        });
    }
}
