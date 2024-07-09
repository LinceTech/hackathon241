package br.com.lince.hackathon.location;

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

/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de foos
 */
@WebServlet("/location/*")
public class LocationServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 5;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(LocationServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditLocation(request, response);
                break;

            case "/delete":
                deleteLocation(request, response);
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
            insertOrUpdateLocation(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de locations carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocationViewData>("location/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(LocationRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locations = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new LocationViewData(locations, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um location, e retorna página atualizada
     */
    private void insertOrUpdateLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocationViewData>("location/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);
        final var id_client = NumberUtils.toInt(request.getParameter("client"), 0);
        final var id_manager = NumberUtils.toInt(request.getParameter("manager"), 0);
        final var id_vehicles = NumberUtils.toInt(request.getParameter("vehicles"), 0);
        final var date_start = Service.verificaData(request.getParameter("initialDate"));;
        final var date_delivery = Service.verificaData(request.getParameter("finalDate"));;
        final var day_value = Double.parseDouble(request.getParameter("dayValue"));;
        final var commission_percentage = Double.parseDouble(request.getParameter("commission"));;
        final var total_value = Double.parseDouble(request.getParameter("totalValue"));;
        final var date_pay = Service.verificaData(request.getParameter("datePay"));;
        final var location = new Location(id, id_client,id_manager,id_vehicles,date_start, date_delivery, day_value,
                commission_percentage, total_value, date_pay);
        final var errors = new HashMap<String, String>();

//        if (bas.isBlank()) {
//            errors.put("basError", "Não pode ser vazio");
//        } else if (bas.length() > 60) {
//            errors.put("basError", "Não pode ser maior que 60 caracteres");
//        }
//
//        if (boo.isBlank()) {
//            errors.put("booError", "Não pode ser vazio");
//        }

        JDBIConnection.instance().withExtension(LocationRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(id)) {
                    dao.update(location);
                } else {
                    dao.insert(location);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locations = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new LocationViewData(locations, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new LocationViewData(errors, location, locations, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de locations
     */
    private void loadFormEditLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Location>("location/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(LocationRepository.class, dao -> {
            renderer.render(dao.findByLocation(id));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de locations
     */
    private void deleteLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(LocationRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
