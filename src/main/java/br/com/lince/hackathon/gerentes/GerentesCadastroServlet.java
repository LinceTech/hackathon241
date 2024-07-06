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
import java.util.logging.Logger;

@WebServlet("/gerentesCadastro/*")
public class GerentesCadastroServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GerentesCadastroServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
                loadFormCadastroGerentes(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";


        if (requestPath.isBlank()) {
            loadFormCadastroGerentes(request, response);
        } else if (requestPath.equals("/cadastrar")) {
            salvarGerente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFormCadastroGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/gerentesCadastro", response);

        renderer.render(new GerentesViewData());
    }

    private void salvarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesCadastroViewData>("gerentes/gerentesCadastroForm", response);
        final String nome = request.getParameter("nome");
        final String cpf = request.getParameter("cpf");
        final String telefone = request.getParameter("telefone");
        final String email = request.getParameter("email");
        final String cidade = request.getParameter("cidade");
        final String estado = request.getParameter("estado");
        final String percentualS = request.getParameter("percentual");
        String dataContratacaoS = request.getParameter("dataContratacao");
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("nome", "Não pode ser vazio");
        } else if (nome.length() > 60) {
            errors.put("nome", "Não pode ser maior que 60 caracteres");
        }
        if (cpf.isBlank()) {
            errors.put("cpf", "CPF não pode ser vazio");
        } else if (cpf.length() > 14) {
            errors.put("cpf", "CPF não pode ser maior que 14 caracteres");
        }
        if (telefone.isBlank()) {
            errors.put("telefone", "Telefone não pode ser vazio");
        } else if (telefone.length() > 15) {
            errors.put("telefone", "Telefone não pode ser maior que 15 caracteres");
        }
        if (email.isBlank()) {
            errors.put("email", "E-mail não pode ser vazio");
        } else if (email.length() > 60) {
            errors.put("email", "E-mail não pode ser maior que 60 caracteres");
        }
        if (cidade.isBlank()) {
            errors.put("cidade", "Cidade não pode ser vazia");
        } else if (cidade.length() > 60) {
            errors.put("cidade", "Cidade não pode ser maior que 60 caracteres");
        }
        if (estado.isBlank()) {
            errors.put("estado", "Estado não pode ser vazio");
        } else if (estado.length() > 60) {
            errors.put("estado", "Estado não pode ser maior que 60 caracteres");
        }
        Float percentual = null;
        if (percentualS.isBlank()) {
            errors.put("percentual", "Percentual de comissão não pode ser vazio");
        } else {
            percentual = Float.parseFloat(percentualS);
            if(percentual > 25){
                errors.put("percentual", "Percentual de comissão não pode ser maior que 25%");
            }
        }
        LocalDate dataContratacao = null;
        if (dataContratacaoS.isBlank()) {
            errors.put("dataContratacao", "Data da contratação não pode ser vazia");
        } else {
            dataContratacao = LocalDate.parse(dataContratacaoS, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        final var gerentes = new Gerentes(nome, cpf, telefone, email, cidade, estado, percentual, dataContratacao);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                dao.insert(gerentes);
                renderer.render(new GerentesCadastroViewData());
            } else {
                renderer.render(new GerentesCadastroViewData(errors, gerentes));
            }
            return null;
        });
    }
}
