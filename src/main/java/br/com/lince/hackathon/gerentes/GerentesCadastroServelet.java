package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/gerentes/cadastro/*")
public class GerentesCadastroServelet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GerentesCadastroServelet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":

            case "/insert":
                loadFormCadastroGerentes(request, response);
                break;

            case "/delete":
//                deleteFoo(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        logger.log(Level.INFO, "TESTE");
        logger.log(Level.INFO, requestPath);

        if (requestPath.isBlank()) {
            loadFormCadastroGerentes(request, response);
        } else if (requestPath.equals("/salvar")) {
            salvarGerente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFormCadastroGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesCadastroViewData>("gerentes/gerentesCadastro", response);

        renderer.render(new GerentesCadastroViewData());
    }

    private void salvarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String nome = request.getParameter("nome");
        final String cpf = request.getParameter("cpf");
        final String telefone = request.getParameter("telefone");
        final String email = request.getParameter("email");
        final String cidade = request.getParameter("cidade");
        final String estado = request.getParameter("estado");
        Float percentual;
        try {
            percentual = Float.parseFloat(request.getParameter("percentual"));
        }catch (Exception e){
            percentual = 0f;
        }
        LocalDate dataContratacao;
        try {
            dataContratacao = LocalDate.parse(request.getParameter("dataContratacao"), DateTimeFormatter.BASIC_ISO_DATE);
        }catch (Exception e){
            dataContratacao = LocalDate.now();
        }
        final var gerentes = new Gerentes(nome, cpf, telefone, email, cidade, estado, percentual, dataContratacao);
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("nome", "Não pode ser vazio");
        } else if (nome.length() > 60) {
            errors.put("nome", "Não pode ser maior que 60 caracteres");
        }
//        logger.log(Level.INFO, "TESTE");
        logger.log(Level.INFO, ""+ errors);
//        if (boo.isBlank()) {
//            errors.put("booError", "Não pode ser vazio");
//        }

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                    dao.insert(gerentes);
            }
            return null;
        });
    }
}
