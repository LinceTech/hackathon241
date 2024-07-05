package br.com.lince.hackathon.manager;


import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooServlet;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.util.Service;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/manager/*")
public class ManagerServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 20;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(ManagerServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

//            case "/edit":
//                loadFormEdit(request, response);
//                break;
//
//            case "/delete":
//                deleteFoo(request, response);
//                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void insertOrUpdateManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var renderer = new TemplateRenderer<FooViewData>("foo/page.hbs", response);
//        final var page.hbs = NumberUtils.toInt(request.getParameter("page.hbs"), 0);

        final var name = request.getParameter("name");
        final var cpf = request.getParameter("cpf");
        final var phone = request.getParameter("phone");
        final var email = request.getParameter("email");
        final var city = request.getParameter("city");
        final var state = request.getParameter("state");
        final var commission_percentage = Double.valueOf(request.getParameter("commission_percentage"));
        final var hiring_date = LocalDate.parse(request.getParameter("hiring_date"));
        final var date_birth = LocalDate.parse(request.getParameter("date_birth"));
        final var manager = new Manager(cpf, name, email, phone, city, state, commission_percentage, hiring_date, date_birth);
        final var errors = new HashMap<String, String>();

        if (cpf.isBlank()) {
            errors.put("CPF Error", "Não pode ser vazio");
        } else if (!Service.validarCPF(cpf)) {
            errors.put("CPF Error", "Informe um CPF valido");
        }

        if (!Service.validarIdade(date_birth)) {
            errors.put("Idade Error", "Não pode ser menor de 18 anos");
        }

        if (!Service.validarEmail(email)) {
            errors.put("Email Error", "Informe um e-mail valido");
        }


        JDBIConnection.instance().withExtension(ManagerRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cpf)) {
                    dao.update(manager);
                } else {
                    dao.insert(manager);
                }
            }

//            final var now = LocalDateTime.now();
//            final var count = dao.count();
//            final var foos = dao.selectPage(page.hbs, PAGE_SIZE);
//
//            if (errors.isEmpty()) {
//                renderer.render(new FooViewData(foos, now, page.hbs, PAGE_SIZE, count));
//            } else {
//                renderer.render(new FooViewData(errors, foo, foos, now, page.hbs, PAGE_SIZE, count));
//            }

            return null;
        });
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
        System.out.printf("Cheguei");
        if (requestPath.isBlank()) {
            //GET
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateManager(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ManagerViewData>("manager/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ManagerRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var manager = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new ManagerViewData(manager, now, page, PAGE_SIZE, count));

            return null;
        });
    }


}
