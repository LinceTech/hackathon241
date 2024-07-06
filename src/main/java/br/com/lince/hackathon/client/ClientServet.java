package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.util.Service;
import br.com.lince.hackathon.viacep.ViaCepRepository;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/client/*")
public class ClientServet extends HttpServlet {

    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 20;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(ClientServet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;
            case "/edit":
                loadFormEditFoo(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        if (requestPath.isBlank()) {
            //GET
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateClient(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientViewData>("client/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var clients = dao.selectPage(page, PAGE_SIZE);
            final var states = Service.findStates();

            renderer.render(new ClientViewData(clients, now, states, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void loadFormEditFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Foo>("client/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            renderer.render(dao.findById(id));
            return null;
        });
    }

    private void insertOrUpdateClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientViewData>("client/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var name = request.getParameter("name");
        final var cpf = request.getParameter("cpf");
        final var birth_date = Service.verificaData(request.getParameter("birthDate"));
        final var phone = request.getParameter("phone");
        final var email = request.getParameter("email");
        final var cep = request.getParameter("cep");
        final var city = request.getParameter("city");
        final var state = request.getParameter("state");
        final var neighborhood = request.getParameter("neighborhood");
        final var street = request.getParameter("street");
        final var number = NumberUtils.toInt(request.getParameter("number"), 0);
        final var client = new Client(name, cpf, birth_date, phone, email, cep, city, state, neighborhood, street, number);
        final var errors = new HashMap<String, String>();

        if (name.isBlank()) {
            errors.put("nameError", "Nome não pode ser vazio");
        }

        if (cpf.isBlank()) {
            errors.put("cpfError", "CPF não pode ser vazio");
        }

        if (phone.isBlank()) {
            errors.put("phoneError", "Telefone não pode ser vazio");
        }

        if (email.isBlank()) {
            errors.put("emailError", "E-mail não pode ser vazio");
        }

        if (cep.isBlank()) {
            errors.put("cepError", "CEP não pode ser vazio");
        }

        if (state.isBlank()) {
            errors.put("stateError", "Estado não pode ser vazio");
        }

        if (neighborhood.isBlank()) {
            errors.put("neighborhoodError", "Bairro não pode ser vazio");
        }

        if (street.isBlank()) {
            errors.put("streetError", "Rua não pode ser vazio");
        }

        if (number == 0) {
            errors.put("numberError", "Estado não pode ser vazio");
        }

        if (!Service.validarCPF(cpf)) {
            errors.put("cpfError", "CPF inválido");
        }

        if (!Service.validarEmail(email)) {
            errors.put("emailError","E-mail inválido");
        }

        if (!Service.validarIdade(client.getBirth_date())) {
            errors.put("birth_dateError","O cliente precisa ser maior de idade");
        }

        if (!ViaCepRepository.exist(cep)){
            errors.put("cepError", "Cep não encontrado");
        }

        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            if (errors.isEmpty()) {
                if (dao.exists(cpf)) {
                    dao.update(client);
                } else {
                    dao.insert(client);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var clients = dao.selectPage(page, PAGE_SIZE);
            final var states = Service.findStates();

            if (errors.isEmpty()) {
                renderer.render(new ClientViewData(clients, now, states, page, PAGE_SIZE, count));
            } else {
                renderer.render(new ClientViewData(errors, client, clients, now, states, page, PAGE_SIZE, count));
            }

            return null;
        });
    }
}
