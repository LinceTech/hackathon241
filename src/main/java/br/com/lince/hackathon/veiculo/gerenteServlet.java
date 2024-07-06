package br.com.lince.hackathon.veiculo;

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
@WebServlet("/gerente/*")
public class gerenteServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(gerenteServlet.class.getName());

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
        final var renderer = new TemplateRenderer<gerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        var orderBy = "CDGERENTE:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDGERENTE:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        JDBIConnection.instance().withExtension(gerenteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE, odb);

            renderer.render(new gerenteViewData(gerentes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<gerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        var orderBy = "CDGERENTE:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDGERENTE:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        final var cdgerente = NumberUtils.toInt(request.getParameter("CDGERENTE"), 0);
        final var nome = request.getParameter("NOME");
        final var cpf = request.getParameter("CPF");
        final var telefone = NumberUtils.toInt(request.getParameter("TELEFONE"),0);
        final var ddd = NumberUtils.toInt(request.getParameter("DDD"),0);
        final var email = request.getParameter("EMAIL");
        final var cidade = request.getParameter("CIDADE");
        final var estado = request.getParameter("ESTADO");
        final var indativo = 1;
        final var pccomissao = NumberUtils.toDouble(request.getParameter("PCCOMISSAO"), 0.0);
        final var dtcontratacao = LocalDate.parse(request.getParameter("DTCONTRATACAO"), DateTimeFormatter.ISO_LOCAL_DATE);
        final var gerente = new Gerente(cdgerente, nome, cpf, telefone, ddd, email, cidade, estado, pccomissao, dtcontratacao, indativo);
        final var errors = new HashMap<String, String>();

        if (cdgerente == 0) {
            errors.put("Código do gerente", "Não pode ser vazio");
        } else if (cdgerente > 999999999) {
            errors.put("Código do gerente", "Não pode ser maior que 9 caracteres");
        }

        if (nome.isBlank()) {
            errors.put("Nome do gerente", "Não pode ser vazio");
        } else if (nome.length() > 100) {
            errors.put("Nome do gerente", "Não pode ser maior que 100 caracteres");
        }

        if (cpf.isBlank()) {
            errors.put("CPF", "Não pode ser vazio");
        } else if (cpf.length() > 14) {
            errors.put("CPF", "Não pode ser maior que 11 caracteres");
//      } else {
////            if(!validarCPF(cpf)){
////                errors.put("CPF", "Inserção inválida");
////            }
       }


        if (telefone == 0) {
            errors.put("Telefone", "Não pode ser vazio");
        } else if (telefone > 999999999) {
            errors.put("Telefone", "Não pode ser maior que 9 caracteres");
        }

        if (email.isBlank()) {
            errors.put("E-mail", "Não pode ser vazio");
        } else if (email.length() > 100) {
            errors.put("E-mail", "Não pode ser maior que 100 caracteres");
        }

        if (cidade.isBlank()) {
            errors.put("Cidade", "Não pode ser vazio");
        } else if (cidade.length() > 100) {
            errors.put("Cidade", "Não pode ser maior que 100 caracteres");
        }

        if (estado.isBlank()) {
            errors.put("Estado", "Não pode ser vazio");
        } else if (estado.length() > 100) {
            errors.put("Estado", "Não pode ser maior que 100 caracteres");
        }

        if (pccomissao == 0) {
            errors.put("Comissão", "Não pode ser vazio");
        } else if (pccomissao > 999999999) {
            errors.put("Comissão", "Não pode ser maior que 9 caracteres");
        }

        if (dtcontratacao == null) {
            errors.put("Data", "Não pode ser vazio");
        }
        log(errors.toString());
        JDBIConnection.instance().withExtension(gerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cdgerente)) {
                    dao.update(gerente);
                } else {
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE, odb);

            if (errors.isEmpty()) {
                renderer.render(new gerenteViewData(gerentes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new gerenteViewData(errors, gerente, gerentes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerente>("gerente/form", response);
        final var gerente = NumberUtils.toInt(request.getParameter("CDGERENTE"), 0);

        JDBIConnection.instance().withExtension(gerenteRepository.class, dao -> {
            renderer.render(dao.findByBar(gerente));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log(request.getParameter("CDGERENTE"));

        final var gerente = Integer.parseInt(request.getParameter("CDGERENTE"));

        JDBIConnection.instance().withExtension(gerenteRepository.class, dao -> {
            dao.delete(gerente);
            loadFullPage(request, response);
            return null;
        });
    }


}
