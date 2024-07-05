package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
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

            renderer.render(new ClientViewData(clients, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void insertOrUpdateClient(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var name = request.getParameter("name");
        final var cpf = request.getParameter("cpf");
        final var birth_date = LocalDate.parse(request.getParameter("birthDate"));
        final var phone = request.getParameter("phone");
        final var email = request.getParameter("email");
        final var cep = request.getParameter("cep");
        final var city = request.getParameter("city");
        System.out.println(request.getParameter("state") + "STATE STATE ");
        final var state = request.getParameter("state");
        final var neighborhood = request.getParameter("neighborhood");
        final var street = request.getParameter("street");
        final var number = Integer.parseInt(request.getParameter("number"));
        final var client = new Client(name, cpf, birth_date, phone, email, cep, city, state, neighborhood, street, number);
        final var errors = new HashMap<String, String>();

        if (cpf.isBlank()) {
            errors.put("cpfError", "CPF não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            if (errors.isEmpty()) {
                if (dao.exists(cpf)) {
                    dao.update(client);
                } else {
                    dao.insert(client);
                }
            }

            return null;
        });
    }
}
