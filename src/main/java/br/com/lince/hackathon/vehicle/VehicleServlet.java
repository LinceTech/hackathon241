package br.com.lince.hackathon.vehicle;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.util.Service;
import br.com.lince.hackathon.util.VehicleType;
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

@WebServlet("/vehicle/*")
public class VehicleServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private static final Logger logger = Logger.getLogger(VehicleServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditVehicle(request, response);
                break;

            case "/delete":
                deleteVehicle(request, response);
                break;

            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void insertOrUpdateVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            final var renderer = new TemplateRenderer<VehicleViewData>("vehicle/page", response);
            final var page = NumberUtils.toInt(request.getParameter("page"), 0);

            final var brand =request.getParameter("brand");
            final var model = request.getParameter("model");
            final var plate = request.getParameter("plate");
            final var color = request.getParameter("color");
            final var year_of_manufacture = Integer.valueOf(request.getParameter("year_of_manufacture"));
            final var daily_cost = Double.valueOf(request.getParameter("daily_cost"));
            final var promotion_description = request.getParameter("promotion_description");
            final var type_fuel = request.getParameter("type_fuel");
            final var type_vehicle = request.getParameter("type_vehicle");
            final var vehicle = new Vehicle(brand, model, plate, color, year_of_manufacture, daily_cost, promotion_description, type_fuel, type_vehicle);
            final var errors = new HashMap<String, String>();


            //validações sobre campos obrigatórios


            JDBIConnection.instance().withExtension(VehicleRepository.class, dao -> {
                // Verificar se ocorreram erros no formulário
                if (errors.isEmpty()) {
                    if (dao.exists(plate)) {
                        dao.update(vehicle);
                    } else {
                        dao.insert(vehicle);
                    }
                }

                final var now = LocalDateTime.now();
                final var count = dao.count();
                final var vehicles = dao.selectPage(page, PAGE_SIZE);
                final var vehiclesType = Service.findTypeVehicle(type_vehicle);


                if (!errors.isEmpty()) {
                    throw new RuntimeException(errors.toString());
                }
                if (errors.isEmpty()) {
                    renderer.render(new VehicleViewData(vehicles, now, page, PAGE_SIZE, count, vehiclesType));
                } else {
                    renderer.render(new VehicleViewData(vehicle, vehicles, now, page,PAGE_SIZE, count, errors, vehiclesType));
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
        if (requestPath.isBlank()) {
            //GET
        } else if (requestPath.equals("/upsert")) {
            insertOrUpdateVehicle(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFormEditVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VehicleEdit>("vehicle/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(VehicleRepository.class, dao -> {
            final var vehicle = dao.findByVehicleId(id);
            final var vehiclesType = Service.findTypeVehicle(vehicle.getType_vehicle());

            final var edit = new VehicleEdit(vehicle, vehiclesType);
            renderer.render(edit);
            return null;
        });
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VehicleViewData>("vehicle/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(VehicleRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var vehicles = dao.selectPage(page, PAGE_SIZE);
            final var vehiclesType = Service.findTypeVehicle("");


            renderer.render(new VehicleViewData(vehicles, now, page, PAGE_SIZE, count, vehiclesType));

            return null;
        });
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = Integer.parseInt(request.getParameter("id"));

        JDBIConnection.instance().withExtension(VehicleRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }
}
