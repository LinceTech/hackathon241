package br.com.lince.hackathon.manager;


import br.com.lince.hackathon.foo.Foo;
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
    private static final int PAGE_SIZE = 5;
    private static final Logger logger = Logger.getLogger(ManagerServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditManager(request, response);
                break;

            case "/delete":
                deleteManager(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void insertOrUpdateManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

        final var renderer = new TemplateRenderer<ManagerViewData>("manager/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
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

        if (!manager.validateCommission(commission_percentage)) {
            errors.put("Comissão Error", "Informe um percentual de comissão valido");
        }

        if (state.isBlank()) {
            errors.put("stateError", "Estado não pode ser vazio");
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

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var managers = dao.selectPage(page, PAGE_SIZE);
            final var states = Service.findStates("");

            if (errors.isEmpty()) {
                renderer.render(new ManagerViewData(managers, now, states, page, PAGE_SIZE, count));
            } else {
                renderer.render(new ManagerViewData(errors, manager, managers,now, states, page, PAGE_SIZE, count));
            }

            return null;
        });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
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

    private void loadFormEditManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ManegerEdit>("manager/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ManagerRepository.class, dao -> {
            final var manager = dao.findByManagerId(id);
            final var states = Service.findStates(manager.getState());

            final var edit = new ManegerEdit(manager, states);
            renderer.render(edit);
            return null;
        });
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ManagerViewData>("manager/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ManagerRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var managers = dao.selectPage(page, PAGE_SIZE);
            final var states = Service.findStates("");


            renderer.render(new ManagerViewData(managers, now, states, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void deleteManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(ManagerRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }


}
