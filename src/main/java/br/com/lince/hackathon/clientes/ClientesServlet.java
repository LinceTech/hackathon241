package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.clientes.ClientesRepository;
import br.com.lince.hackathon.clientes.ClientesViewData;
import br.com.lince.hackathon.gerentes.GerentesRepository;
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

    private void criarOuAtualizaCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("clientes/ClienteLista", response);

        final var id = NumberUtils.toLong(request.getParameter("id"), 0);
        final var nome = request.getParameter("nome").trim();
        final var cpf = request.getParameter("cpf").trim().replace("\\D", "");
        final var dtNascimento = request.getParameter("dataNascimento").trim();
        final var ddd = NumberUtils.toInt(request.getParameter("ddd"), 0);
        final var telefone = NumberUtils.toInt(request.getParameter("telefone"), 0);
        final var email = request.getParameter("email").trim();
        final var cidade = request.getParameter("cidade").trim();
        final var estado = request.getParameter("estado").trim();
        final var bairro = request.getParameter("bairro").trim();
        final var rua = request.getParameter("rua").trim();
        final var numero = request.getParameter("numero").trim();
        final var cep = request.getParameter("cep").trim();

        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final var dataNascimento = LocalDate.parse(dtNascimento, formatter);

        Clientes clientes = new Clientes();

        clientes.setId(id);
        clientes.setNome(nome);
        clientes.setCpf(cpf);
        clientes.setDdd(ddd);
        clientes.setTelefone(telefone);
        clientes.setEmail(email);
        clientes.setCidade(cidade);
        clientes.setEstado(estado);
        clientes.setBairro(bairro);
        clientes.setRua(rua);
        clientes.setNumero(numero);
        clientes.setCep(cep);
        clientes.setDataNascimento(dataNascimento);


        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            if (dao.existeCliente(id)) {
                dao.atualizaCliente(clientes);
            } else {
                dao.insereCliente(clientes);
            }

            final var listaClientes = dao.consultaPaginacao(0, 15);

            renderer.render(new ClientesViewData(listaClientes));

            return null;
        });
    }

    private void deletarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClienteLista", response);

        final var clienteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            dao.deleteCliente(clienteID);

            renderer.render(new ClientesViewData(dao.consultaPaginacao(0, 15)));

            return null;
        });
    }

    void carregaClientesModal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Clientes>("/clientes/ClienteModal", response);

        final var clienteID = NumberUtils.toLong(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            Clientes cliente = new Clientes();

            if(clienteID != 0){
                cliente = dao.pegaClientesPeloID(clienteID);
            }

            renderer.render(cliente);

           return null;
        });
    }

    void carregaClientesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClientePage", response);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            renderer.render(new ClientesViewData(dao.consultaPaginacao(0, 15)));

            return null;
        });
    }

}
