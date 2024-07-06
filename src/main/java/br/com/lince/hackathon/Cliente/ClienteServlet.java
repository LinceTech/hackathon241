package br.com.lince.hackathon.Cliente;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import br.com.lince.hackathon.Utils.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/cliente/*")
public class ClienteServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    private static final Logger logger = Logger.getLogger(ClienteServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditCliente(request, response);
                break;

            case "/delete":
                deleteCliente(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
        System.out.println(requestPath);

        if (requestPath.isBlank()) {
            loadFullPage(request, response);
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateCliente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de clientes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClienteViewData>("cliente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var clientes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new ClienteViewData(clientes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void insertOrUpdateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClienteViewData>("cliente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var nome = request.getParameter("nome");
        final var cpf = NumberUtils.toInt(request.getParameter("cpf"));
        final var dataNascimento = Funcoes.inverteData(request.getParameter("dataNascimento"));
        final var telefone = NumberUtils.toInt(request.getParameter("telefone").replaceAll("-", "").replaceAll(" ", ""));
        final var email = request.getParameter("email");
        final var cep = NumberUtils.toInt(request.getParameter("cep").replaceAll("-", ""));
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var bairro = request.getParameter("bairro");
        final var rua = request.getParameter("rua");
        final var numero = NumberUtils.toInt(request.getParameter("numero"));

        final var cliente = new Cliente(nome, cpf, dataNascimento, telefone, email, cep, cidade, estado, bairro, rua, numero);
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("nomeError", "Não pode ser vazio");
        } else if (nome.length() > 60) {
            errors.put("nomeError", "Nome não pode ser maior que 60 caracteres");
        }

        if (cpf == 0) {
            errors.put("cpfError", "CPF não pode ser vazio");
        }

        if (dataNascimento == 0) {
            errors.put("dataNascimentoError", "Data de nascimento não pode ser vazio");
        }

        if (telefone == 0) {
            errors.put("telefoneError", "telefone não pode ser vazio");
        }

        if (email.isBlank()) {
            errors.put("emailError", "E-mail não pode ser vazio");
        }

        if (cep == 0) {
            errors.put("cepError", "CEP não pode ser vazio");
        }

        if (cidade.isBlank()) {
            errors.put("cidadeError", "Cidade não pode ser vazio");
        }

        if (estado.isBlank()) {
            errors.put("estadoError", "Cidade não pode ser vazio");
        }

        if (bairro.isBlank()) {
            errors.put("bairroError", "Bairro não pode ser vazio");
        }

        if (rua.isBlank()) {
            errors.put("ruaError", "Rua não pode ser vazio");
        }

        if (numero == 0) {
            errors.put("numeroError", "Número do local não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cpf)) {
                    dao.update(cliente);
                } else {
                    System.out.println(cliente);
                    dao.insert(cliente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var clientes = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new ClienteViewData(clientes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new ClienteViewData(errors, cliente, clientes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de clientes
     */
    private void loadFormEditCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Cliente>("cliente/form", response);
        final var cpf = request.getParameter("cpf");

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            renderer.render(dao.findByCPF(NumberUtils.toInt(request.getParameter("cpf"))));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de clientes
     */
        private void deleteCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var cpf = Integer.parseInt(request.getParameter("cpf"));

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            dao.delete(cpf);
            loadFullPage(request, response);
            return null;
        });
    }
}
