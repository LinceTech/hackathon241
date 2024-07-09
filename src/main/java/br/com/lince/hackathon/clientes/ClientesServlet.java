package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.clientes.ClientesRepository;
import br.com.lince.hackathon.clientes.ClientesViewData;
import br.com.lince.hackathon.gerentes.GerenteFiltros;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.gerentes.GerentesViewData;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/clientes/*")
public class ClientesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ClientesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                carregaClientesPage(request, response);
                break;
            case "/new":
            case "/edit":
                carregaClientesModal(request, response);
                break;
            case "/delete":
                deletarClientes(request, response);
                break;
            case "/listar":
                listarClientes(request, response);
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
                criarOuAtualizaCliente(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClienteLista", response);

        final var nome = request.getParameter("nome").trim();
        final var cidade = request.getParameter("cidade").trim();
        final var proximaPagina = Boolean.parseBoolean(request.getParameter("proximaPagina").trim());
        final var numeroPaginaReq = NumberUtils.toInt(request.getParameter("numeroPagina").trim(), 0);

        final var filtros = new ClientesFiltros(nome, cidade);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            int numeroPagina = numeroPaginaReq;

            if (proximaPagina) {
                numeroPagina++;
            } else {
                numeroPagina--;

                numeroPagina = numeroPagina <= 0 ? 0 : numeroPagina;
            }

            List<Clientes> list = dao.consultaPaginacao(numeroPagina, 15, filtros);

            renderer.render(new ClientesViewData(list, nome, cidade, numeroPagina));

            return null;
        });
    }

    private void criarOuAtualizaCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final HashMap<String, String> erros = new HashMap<>();

        Long id = 0l;
        String nome = "";
        String cpf = "";
        String dtNascimento = "";
        int ddd = 0;
        int telefone = 0;
        String email = "";
        String cidade = "";
        String estado = "";
        String bairro = "";
        String rua = "";
        int numero = 0;
        int cep = 0;

        if (request.getParameter("id") != null) {
            id = NumberUtils.toLong(request.getParameter("id"), 0);
        }
        if (request.getParameter("nome") != null) {
            nome = request.getParameter("nome");
        }
        if (request.getParameter("cpf") != null) {
            cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        }
        if (request.getParameter("dataNascimento") != null) {
            dtNascimento = request.getParameter("dataNascimento");
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
        if (request.getParameter("bairro") != null) {
            bairro = request.getParameter("bairro");
        }
        if (request.getParameter("rua") != null) {
            rua = request.getParameter("rua");
        }
        if (request.getParameter("cep") != null) {
            cep = NumberUtils.toInt(request.getParameter("cep"), 0);
        }
        if (request.getParameter("numero") != null) {
            numero = NumberUtils.toInt(request.getParameter("numero"), 0);
        }

        Clientes clientes = new Clientes(
                id,
                nome,
                cpf,
                LocalDate.now(),
                ddd,
                telefone,
                email,
                cep,
                cidade,
                estado,
                bairro,
                rua,
                numero
        );

        final Long idConsulta = id;
        final String cpfConsulta = cpf;

        if (clientes.getNome().isBlank()) {
            erros.put("nomeErro", "Informe o nome");
        }
        if (clientes.getCpf().isBlank()) {
            erros.put("cpfErro", "Informe o CPF");
        }
        if (dtNascimento.isBlank()) {
            erros.put("dataNascimentoErro", "Informe a data de nascimento");
        } else {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate dataNascimento = LocalDate.parse(dtNascimento, formatter);

            if (dataNascimento.isAfter(LocalDate.now().minusYears(18))) {
                erros.put("dataNascimentoErro", "Cliente deve ser maior de 18 anos");
            }
            clientes.setDataNascimento(dataNascimento);
        }
        if (clientes.getDdd() == 0) {
            erros.put("dddErro", "Informe o numero");
        }
        if (clientes.getTelefone() == 0) {
            erros.put("telefoneErro", "Informe o telefone");
        }
        if (clientes.getEmail().isBlank()) {
            erros.put("emailErro", "Informe o email");
        }
        if (clientes.getCidade().isBlank()) {
            erros.put("cidadeErro", "Informe o cidade");
        }
        if (clientes.getEstado().isBlank()) {
            erros.put("estadoErro", "Informe o estado");
        }
        if (clientes.getRua().isBlank()) {
            erros.put("ruaErro", "Informe a Rua");
        }
        if (clientes.getBairro().isBlank()) {
            erros.put("bairroErro", "Informe o Bairro");
        }
        if (clientes.getNumero() == 0) {
            erros.put("numeroErro", "Informe o número");
        }
        if (clientes.getCep() == 0) {
            erros.put("cepErro", "Informe o CEP");
        }




        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            final var rendererLista = new TemplateRenderer<ClientesViewData>("/clientes/ClienteLista", response);
            final var rendererModal = new TemplateRenderer<Clientes>("/clientes/ClienteModal", response);

            if(erros.isEmpty()){
                if (dao.existeCliente(idConsulta)) {
                    if (dao.existeCpf(cpfConsulta, idConsulta)) {
                        erros.put("cpfErro", "Já Cadastrado!");
                    } else {
                        dao.atualizaCliente(clientes);
                    }
                } else if (dao.existeCpf(cpfConsulta, idConsulta)) {

                } else {
                    dao.insereCliente(clientes);
                }
            }

            if (erros.isEmpty()) {
                List<Clientes> list = dao.consultaPaginacao(0, 15, new ClientesFiltros("", ""));

                rendererLista.render(new ClientesViewData(list, "", "", 0));
            }else{
                clientes.setErros(erros);

                response.setStatus(500);
                response.setHeader("HX-Reswap", "innerHTML");

                rendererModal.render(clientes);
            }

            return null;
        });
    }

    private void deletarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClienteLista", response);

        final var clienteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            dao.deleteCliente(clienteID);

            List<Clientes> list = dao.consultaPaginacao(0, 15, new ClientesFiltros("", ""));

            renderer.render(new ClientesViewData(list, "", "", 0));

            return null;
        });
    }

    void carregaClientesModal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Clientes>("/clientes/ClienteModal", response);

        final var clienteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            Clientes cliente = new Clientes();

            if (clienteID != 0) {
                cliente = dao.pegaClientesPeloID(clienteID);
            }

            renderer.render(cliente);

            return null;
        });
    }

    void carregaClientesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClientePage", response);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            List<Clientes> list = dao.consultaPaginacao(0, 15, new ClientesFiltros("", ""));

            renderer.render(new ClientesViewData(list, "", "", 0));

            return null;
        });
    }

}
