package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.foo.Foo;
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
import java.time.LocalDateTime;
import java.util.HashMap;
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
            case "/save":
                insereCliente(request, response);
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

    void insereCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<FooViewData>("clientes/ClientePage", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var nome = request.getParameter("nome");
        final var boo = request.getParameter("boo");
        final var clientes = new Clientes(nome);
        final var errors = new HashMap<String, String>();

        if (nome.isBlank()) {
            errors.put("basError", "Não pode ser vazio");
        } else if (nome.length() > 60) {
            errors.put("basError", "Não pode ser maior que 60 caracteres");
        }

        if (boo.isBlank()) {
            errors.put("booError", "Não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(bar)) {
                    dao.update(foo);
                } else {
                    dao.insert(foo);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var foos = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new FooViewData(foos, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new FooViewData(errors, foo, foos, now, page, PAGE_SIZE, count));
            }

            return null;
        });

        response.sendRedirect("/clientes/ClientePage");
    }
}
