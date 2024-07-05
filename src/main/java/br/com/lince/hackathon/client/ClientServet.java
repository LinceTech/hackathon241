package br.com.lince.hackathon.client;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooServlet;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

    private void insertOrUpdateClient(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var name = request.getParameter("name");
        final var cpf = request.getParameter("cpf");
        final var birth_date = LocalDate.parse(request.getParameter("birth_date"));
        final var phone = request.getParameter("phone");
        final var email = request.getParameter("email");
        final var cep = request.getParameter("cep");
        final var city = request.getParameter("city");
        final var state = request.getParameter("state");
        final var neighborhood = request.getParameter("neighborhood");
        final var street = request.getParameter("street");
        final var number = Integer.parseInt(request.getParameter("number"));
        final var client = new Client();
        final var errors = new HashMap<String, String>();


    }
}
