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
import br.com.lince.hackathon.veiculos.VeiculoOpcao;
import br.com.lince.hackathon.veiculos.Veiculos;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "/save":
                criaLocacao(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void criaLocacao(HttpServletRequest request, HttpServletResponse response) {
        Long idCliente = 0L;
        Long idGerente = 0L;
        Long idVeiculo = 0L;
        String dataInicioReq = "";
        String dataEntregaReq = "";
        double valoDiario = 0;

        if(request.getParameter("cliente") != null) {
            if(!request.getParameter("cliente").isBlank()) {
                idCliente = Long.parseLong(request.getParameter("cliente"));
            }
        }
        if(request.getParameter("gerente") != null) {
            if(!request.getParameter("gerente").isBlank()) {
                idGerente = Long.parseLong(request.getParameter("gerente"));
            }
        }
        if(request.getParameter("veiculo") != null) {
            if(!request.getParameter("veiculo").isBlank()) {
                idVeiculo = Long.parseLong(request.getParameter("veiculo"));
            }
        }
        if(request.getParameter("dataInicio") != null) {
            dataInicioReq = request.getParameter("dataInicio");
        }
        if(request.getParameter("dataEntrega") != null) {
            dataEntregaReq = request.getParameter("dataEntrega");
        }
        if(request.getParameter("cliente") != null) {
            if(!request.getParameter("cliente").isBlank()) {
                valoDiario = Double.parseDouble(request.getParameter("cliente"));
            }
        }

        final var erros = new HashMap<String, String>();

        if(idCliente == 0){
            erros.put("idClienteErro", "Informe o cliente");
        }
        if(idGerente == 0){
            erros.put("idGerenteErro", "Informe o gerente");
        }
        if(idVeiculo == 0){
            erros.put("idVeiculoErro", "Informe o veiculo");
        }
        if(dataInicioReq.isBlank()){
           erros.put("dataInicioErro", "Informe a data do Inicio");
        }
        if(dataEntregaReq.isBlank()){
            erros.put("dataEntregaReq", "Informe a data do Entrega");
        }
        if(valoDiario == 0.0){
            erros.put("valorDiarioErro", "Informe a valor da Diaria");
        }
    }

    private void formularioLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoFormViewData>("locacoes/LocacoesModal", response);


        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            List<ClienteOpcao> clienteOpcoes = dao.listaOpcoesClientes();
            List<GerenteOpcao> gerenteOpcoes = dao.listaOpcoesGerentes();
            List<VeiculoOpcao> veiculoOpcoes = dao.listaOpcoesVeiculos();


            renderer.render(
                    new LocacaoFormViewData(
                            new Locacao(),
                            clienteOpcoes,
                            gerenteOpcoes,
                            veiculoOpcoes
                    )
            );

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
