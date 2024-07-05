package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.standard.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/clientes/*")
public class ClientesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaClientesPage(request, response);
                break;
            case "/new":
                carregaClientesModal(response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    void carregaClientesModal(HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Clientes>("/clientes/ClienteModal", response);

        renderer.render(new Clientes());
    }

    void carregaClientesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Clientes>("/clientes/ClientePage", response);

        renderer.render(new Clientes());
    }
}
