package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.endereco.Endereco;
import br.com.lince.hackathon.foo.Foo;
import br.com.lince.hackathon.foo.FooRepository;
import br.com.lince.hackathon.foo.FooServlet;
import br.com.lince.hackathon.foo.FooViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

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

@WebServlet("/cliente/*")
public class ClienteServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ClienteServlet.class.getName());

    private static final int PAGE_SIZE = 5;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditFoo(request, response);
                break;

            case "/delete":
                deleteFoo(request, response);
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
            insertOrUpdateFoo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<FooViewData>("cliente/cadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastro"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var foos = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new FooViewData(foos, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<FooViewData>("cliente/cadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("cadastro"), 0);

        final var dt_nascimento = NumberUtils.toInt(request.getParameter("dt_nascimento"), 0);
        final var nr_telefone = NumberUtils.toInt(request.getParameter("nr_telefone"), 0);
        final var nr_residencia = NumberUtils.toInt(request.getParameter("nr_residencia"), 0);

        final var ds_email = request.getParameter("ds_email");
        final var nm_cidade = request.getParameter("nm_cidade");
        final var nm_estado = request.getParameter("nm_estado");
        final var nm_bairro = request.getParameter("nm_bairro");
        final var nm_rua = request.getParameter("nm_rua");
        final var nm_cliente = request.getParameter("nm_cliente");
        final var nr_cep = request.getParameter("nr_cep");
        final var nr_cpf = request.getParameter("nr_cpf");

        LocalDate localDate  = LocalDate.now();


        Endereco endereco = new Endereco(nm_bairro, nr_cep, nm_cidade, nm_estado, nr_residencia, nm_rua);

        final var cliente = new Cliente(nm_cliente,  nr_cpf, localDate,  nr_telefone, ds_email, endereco);

        final var errors = new HashMap<String, String>();

        if (nm_cliente.isBlank()) {
            errors.put("basError", "Não pode ser vazio");
        } else if (nm_cliente.length() > 100) {
            errors.put("basError", "Não pode ser maior que 60 caracteres");
        }



        JDBIConnection.instance().withExtension(ClienteRepository.class, cliente -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (cliente.exists(nr_cpf) {
                    cliente.update(cliente);
                } else {
                    cliente.insert(cliente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = cliente.count();
            final var clientes = cliente.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new FooViewData(clientes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new FooViewData(errors, cliente, clientes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Foo>("cliente/form", response);
        final var bar = NumberUtils.toInt(request.getParameter("bar"), 0);

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            renderer.render(dao.findByBar(bar));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var bar = Integer.parseInt(request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, dao -> {
            dao.delete(bar);
            loadFullPage(request, response);
            return null;
        });
    }
}
