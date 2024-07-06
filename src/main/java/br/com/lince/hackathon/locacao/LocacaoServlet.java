package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.clientes.ClienteOpcao;
import br.com.lince.hackathon.clientes.Clientes;
import br.com.lince.hackathon.clientes.ClientesRepository;
import br.com.lince.hackathon.gerentes.GerenteOpcao;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.gerentes.GerentesRepository;
import br.com.lince.hackathon.gerentes.GerentesViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/locacoes/*")
public class LocacaoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LocacaoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaPagina(response);
                break;
            case "/new":
                formularioLocacao(request, response);
                break;
            case "/delete":
//                deletarGerente(request, response);
                break;
            case "/listar":
                listarLocacoes(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void formularioLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoFormViewData>("locacoes/LocacoesModal", response);


        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            List<ClienteOpcao> clienteOpcoes = dao.listaOpcoesClientes();
            List<GerenteOpcao> gerenteOpcoes = dao.listaOpcoesGerentes();

            renderer.render(new LocacaoFormViewData(new Locacao(), clienteOpcoes, gerenteOpcoes));

            return null;
        });
    }

    private void listarLocacoes(HttpServletRequest request, HttpServletResponse response) {

    }

    private void carregaPagina(HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacoes/LocacoesPage", response);

        renderer.render(new LocacaoViewData(List.of(new Locacao()), "", "", "", false, 0));
    }
}
