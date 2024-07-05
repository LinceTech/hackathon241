package br.com.lince.hackathon.clientes;

import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.clientes.ClientesRepository;
import br.com.lince.hackathon.clientes.ClientesViewData;
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

        final var id = org.apache.commons.lang3.math.NumberUtils.toLong(request.getParameter("id"), 0);
        final var nome = request.getParameter("nome").trim();
        final var cpf = request.getParameter("cpf").trim().replace("\\D", "");
        final var dataNascimento = request.getParameter("dataNascimento").trim();
        final var ddd = org.apache.commons.lang3.math.NumberUtils.toInt(request.getParameter("ddd"), 0);
        final var telefone = org.apache.commons.lang3.math.NumberUtils.toInt(request.getParameter("telefone"), 0);
        final var email = request.getParameter("email").trim();
        final var cidade = request.getParameter("cidade").trim();
        final var estado = request.getParameter("estado").trim();
        final var bairro = request.getParameter("bairro").trim();
        final var rua = request.getParameter("rua").trim();
        final var numero = request.getParameter("numero").trim();
        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final var dtNascimento = LocalDate.parse(dataNascimento, formatter);

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
        clientes.setDataNascimento(dtNascimento);


        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            if (dao.verificaAlocacaoCliente(id)) {
                dao.atualizaCliente(clientes);
            } else {
                dao.insereCliente(clientes);
            }

            final var listaClientes = dao.consultaPaginacao(0, 15);

            renderer.render(new ClientesViewData(listaClientes));

            return null;
        });
    }

    private void deletarClientes(HttpServletRequest request, HttpServletResponse response) {

    }

    void carregaClientesModal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Clientes>("/clientes/ClienteModal", response);

        renderer.render(new Clientes());
    }

    void carregaClientesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("/clientes/ClientePage", response);

        JDBIConnection.instance().withExtension(ClientesRepository.class, dao -> {
            renderer.render(new ClientesViewData(dao.consultaPaginacao(0, 15)));

            return null;
        });
    }

    void insereCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var renderer = new TemplateRenderer<FooViewData>("clientes/ClientePage", response);
//        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
//        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var nome = request.getParameter("nome");
//        final var boo = request.getParameter("boo");
//        final var clientes = new Clientes(nome);
//        final var errors = new HashMap<String, String>();
//
//        if (nome.isBlank()) {
//            errors.put("basError", "Não pode ser vazio");
//        } else if (nome.length() > 60) {
//            errors.put("basError", "Não pode ser maior que 60 caracteres");
//        }
//
//        if (boo.isBlank()) {
//            errors.put("booError", "Não pode ser vazio");
//        }
//
//        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
//            // Verificar se ocorreram erros no formulário
//            if (errors.isEmpty()) {
//                if (dao.exists(bar)) {
//                    dao.update(foo);
//                } else {
//                    dao.insert(foo);
//                }
//            }
//
//            final var now = LocalDateTime.now();
//            final var count = dao.count();
//            final var foos = dao.selectPage(page, PAGE_SIZE);
//
//            if (errors.isEmpty()) {
//                renderer.render(new FooViewData(foos, now, page, PAGE_SIZE, count));
//            } else {
//                renderer.render(new FooViewData(errors, foo, foos, now, page, PAGE_SIZE, count));
//            }
//
//            return null;
//        });

        response.sendRedirect("/clientes/ClientePage");
    }
}
