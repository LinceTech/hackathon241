package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooServlet;
import br.com.lince.hackathon.foo.FooViewData;
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

@WebServlet("/cliente/*")
public class clienteServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FooServlet.class.getName());

    private static final int PAGE_SIZE = 5;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditFoo(request, response);
                break;

            case "/delete":
                deleteFoo(request, response);
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
            insertOrUpdateFoo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<FooViewData>("cliente/cadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastro"), 0);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var foos = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new FooViewData(foos, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<FooViewData>("cliente/cadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastro"), 0);
        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);
        final var bas = request.getParameter("bas");
        final var boo = request.getParameter("boo");
        final var foo = new Foo(bar, bas, boo);
        final var errors = new HashMap<String, String>();

        if (bas.isBlank()) {
            errors.put("basError", "Não pode ser vazio");
        } else if (bas.length() > 60) {
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
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Foo>("cliente/form", response);
        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            renderer.render(dao.findByBar(bar));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var bar = Integer.parseInt(request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            dao.delete(bar);
            loadFullPage(request, response);
            return null;
        });
    }
}
