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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/gerentes/*")
public class GerentesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GerentesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaPagina(response);
                break;
            case "/new":
            case "/edit":
                formularioGerente(request, response);
                break;
            case "/delete":
                deletarGerente(request, response);
                break;
            case "/listar":
                listarGerentes(request, response);
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

    private void listarGerentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("/gerentes/GerenteLista", response);

        final var nome = request.getParameter("nome").trim();
        final var documento = request.getParameter("documento").trim();
        final var cidade = request.getParameter("cidade").trim();
        final var estado = request.getParameter("estado").trim();
        final var proximaPagina = Boolean.parseBoolean(request.getParameter("proximaPagina").trim());

        final var numeroPaginaReq = NumberUtils.toInt(request.getParameter("numeroPagina").trim(), 0);

        final var filtros = new GerenteFiltros(nome, documento, cidade, estado);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            int numeroPagina = numeroPaginaReq;

            if (proximaPagina) {
                numeroPagina++;
            } else {
                numeroPagina--;

                numeroPagina = numeroPagina <= 0 ? 0 : numeroPagina;
            }

            List<Gerentes> list = dao.consultaPaginacao(numeroPagina, 15, filtros);

            renderer.render(new GerentesViewData(null, list, nome, documento, cidade, estado, numeroPagina));

            return null;
        });
    }

    private void criarOuAtualizaGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var erros = new HashMap<String, String>();

        Long id = 0l;
        String nome = "";
        String cpf = "";
        int ddd = 0;
        int telefone = 0;
        String email = "";
        String cidade = "";
        String estado = "";
        double percentualComissao = 0;
        String dataContratacaoReq = "";

        if (request.getParameter("id") != null) {
            id = NumberUtils.toLong(request.getParameter("id"), 0);
        }
        if (request.getParameter("nome") != null) {
            nome = request.getParameter("nome");
        }
        if (request.getParameter("cpf") != null) {
            cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        }
        if (request.getParameter("ddd") != null) {
            ddd = NumberUtils.toInt(request.getParameter("ddd"), 0);
        }
        if (request.getParameter("telefone") != null) {
            telefone = NumberUtils.toInt(request.getParameter("telefone"), 0);
        }
        if (request.getParameter("email") != null) {
            email = request.getParameter("email");
        }
        if (request.getParameter("cidade") != null) {
            cidade = request.getParameter("cidade");
        }
        if (request.getParameter("estado") != null) {
            estado = request.getParameter("estado");
        }
        if (request.getParameter("percentualComissao") != null) {
            percentualComissao = NumberUtils.toDouble(request.getParameter("percentualComissao").replace(",", "."));
        }
        if (request.getParameter("dataContratacao") != null) {
            dataContratacaoReq = request.getParameter("dataContratacao");
        }

        Gerentes gerente = new Gerentes(
                id,
                nome,
                cpf,
                ddd,
                telefone,
                email,
                cidade,
                estado,
                percentualComissao,
                null,
                false
        );

        if (gerente.getNome().isBlank()) {
            erros.put("nomeErro", "Informe o nome");
        }
        if (gerente.getCpf().isBlank()) {
            erros.put("cpfErro", "Informe o CPF");
        }
        if (gerente.getDdd() == 0) {
            erros.put("dddErro", "Informe o numero");
        }
        if (gerente.getTelefone() == 0) {
            erros.put("telefoneErro", "Informe o telefone");
        }
        if (gerente.getEmail().isBlank()) {
            erros.put("emailErro", "Informe o email");
        }
        if (gerente.getCidade().isBlank()) {
            erros.put("cidadeErro", "Informe o cidade");
        }
        if (gerente.getCidade().isBlank()) {
            erros.put("cidadeErro", "Informe o cidade");
        }
        if (gerente.getEstado().isBlank()) {
            erros.put("estadoErro", "Informe o estado");
        }
        if (gerente.getPercentualComissao() == 0) {
            erros.put("percentualComissaoErro", "Informe o percentual de comissão");
        }else if(percentualComissao > 25.0){
            erros.put("percentualComissaoErro", "O percentual de comissão não pode ser maior que 25%");
        }
        if (dataContratacaoReq.isBlank()) {
            erros.put("dataContratacaoErro", "Informe data de contratacao");
        }else{
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate dataContratacao = LocalDate.parse(dataContratacaoReq, formatter);

            gerente.setDataContratacao(dataContratacao);
        }

        final Long idConsulta = id;

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            var renderer = new TemplateRenderer<Gerentes>("gerentes/GerenteModal", response);

            if (erros.isEmpty()) {
                gerente.setSalvou(true);
                if (dao.existeGerente(idConsulta)) {
                    dao.atualizaGerente(gerente);
                } else {
                    dao.insereGerente(gerente);
                }
            }

            if(erros.isEmpty()){
                renderer.render(gerente);
            }else{
                gerente.setErros(erros);
                renderer.render(gerente);
            }

            return null;
        });
    }

    private void deletarGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("/gerentes/GerenteLista", response);

        final var gerenteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            dao.deleteGerente(gerenteID);

            List<Gerentes> list = dao.consultaPaginacao(0, 15, new GerenteFiltros("", "", "", ""));

            renderer.render(new GerentesViewData(null, list, "", "", "", "", 0));

            return null;
        });
    }

    private void formularioGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerentes>("/gerentes/GerenteModal", response);

        final var gerenteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {
            var gerente = new Gerentes();

            if (gerenteID != 0) {
                gerente = dao.pegaGerentesPeloID(gerenteID);
            }

            renderer.render(gerente);

            return null;
        });
    }

    private void carregaPagina(HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerentesViewData>("/gerentes/GerentePage", response);

        JDBIConnection.instance().withExtension(GerentesRepository.class, dao -> {

            List<Gerentes> list = dao.consultaPaginacao(0, 15, new GerenteFiltros("", "", "", ""));

            renderer.render(new GerentesViewData(null, list, "", "", "", "", 0));

            return null;
        });
    }

}
