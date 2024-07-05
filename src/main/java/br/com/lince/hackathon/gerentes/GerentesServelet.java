package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.foo.FooServlet;
import br.com.lince.hackathon.standard.TemplateRenderer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/gerentesCadastro/*")
public class GerentesServelet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(FooServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
                loadFormCadastroGerentes(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFormCadastroGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/gerentesCadastro", response);

        renderer.render(new GerentesViewData());
    }
}
