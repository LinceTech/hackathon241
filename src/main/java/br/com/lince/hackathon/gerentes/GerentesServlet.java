package br.com.lince.hackathon.gerentes;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/gerentes/*")
public class GerentesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GerentesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaPagina(request, response);
                break;
            case "/new":
            case "/edit":
                formularioGerente(request, response);
                break;
            case "/delete":
                deletarGerente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "/save":
                criarOuAtualizaGerente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void criarOuAtualizaGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("gerentes/GerenteLista", response);

        final var id = NumberUtils.toLong(request.getParameter("id"), 0);
        final var nome = request.getParameter("nome").trim();
        final var cpf = request.getParameter("cpf").trim().replace("\\D", "");
        final var ddd = NumberUtils.toInt(request.getParameter("ddd"), 0);
        final var telefone = NumberUtils.toInt(request.getParameter("telefone"), 0);
        final var email = request.getParameter("email").trim();
        final var cidade = request.getParameter("cidade").trim();
        final var estado = request.getParameter("estado").trim();
        final var percentualComissao = NumberUtils.toDouble(request.getParameter("percentualComissao").replace(",", "."), 0);
        final var dataContratacaoReq = request.getParameter("dataContratacao").trim();

        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final var dataContratacao = LocalDate.parse(dataContratacaoReq, formatter);

        Gerentes gerente = new Gerentes();

        gerente.setId(id);
        gerente.setNome(nome);
        gerente.setCpf(cpf);
        gerente.setDdd(ddd);
        gerente.setTelefone(telefone);
        gerente.setEmail(email);
        gerente.setCidade(cidade);
        gerente.setEstado(estado);
        gerente.setPercentualComissao(percentualComissao);
        gerente.setDataContratacao(dataContratacao);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            if (dao.existeGerente(id)) {
                dao.atualizaGerente(gerente);
            } else {
                dao.insereGerente(gerente);
            }

            final var listaGerentes = dao.consultaPaginacao(0, 15);

            renderer.render(new GerentesViewData(listaGerentes));

            return null;
        });
    }

    private void deletarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("/gerentes/GerenteLista", response);

        final var gerenteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            dao.deleteGerente(gerenteID);

            renderer.render(new GerentesViewData(dao.consultaPaginacao(0, 15)));

            return null;
        });
    }

    private void formularioGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerentes>("/gerentes/GerenteModal", response);

        final var gerenteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            var gerente = new Gerentes();

            if(gerenteID != 0){
                gerente = dao.pegaGerentesPeloID(gerenteID);
            }

            renderer.render(gerente);

           return null;
        });
    }

    private void carregaPagina(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("/gerentes/GerentePage", response);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {

            renderer.render(new GerentesViewData(dao.consultaPaginacao(0, 15)));

            return null;
        });
    }

}
