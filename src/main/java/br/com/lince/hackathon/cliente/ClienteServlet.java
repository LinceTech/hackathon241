package br.com.lince.hackathon.cliente;

import br.com.lince.hackathon.foo.FooRepository;
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
            insertOrUpdateCliente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClienteViewData>("cliente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, cliente -> {
            final var now = LocalDateTime.now();
            final var count = cliente.count();
            final var clientes = cliente.selectPage(page, PAGE_SIZE);

            logger.severe(clientes+" <-- clientes");

            renderer.render(new ClienteViewData(clientes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClienteViewData>("cliente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

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


        final var cliente_insert = new Cliente(nm_cliente, nr_cpf, localDate, nr_telefone, ds_email, nm_bairro, nr_cep, nm_cidade, nm_estado, nr_residencia, nm_rua);

        final var errors = new HashMap<String, String>();

        if (nm_cliente.isBlank()) {
            errors.put("basError", "Nome não pode ser vazio");
        } else if (nm_cliente.length() > 100) {
            errors.put("basError", "Nome não pode ser maior que 100 caracteres");
        }

        if(nr_cpf.isBlank()){
            errors.put("basError", "CPF pode ser vazio");
        }

        if(nr_telefone == 0){
            errors.put("basError", "Telefone não pode ser vazio");
        } else if(String.valueOf(nr_telefone).length() < 11){
            errors.put("basError", "Telefone invalido!");
        }

        if(ds_email.isBlank()){
            errors.put("basError", "E-mail não pode ser vazio");
        } else if (ds_email.length() > 100) {
            errors.put("basError", "E-mail não pode ser maior que 100 caracteres");
        }

        if(nr_cep.isBlank()){
            errors.put("basError", "C.E.P não pode ser vazio");
        } else if (nr_cep.length() > 100) {
            errors.put("basError", "C.E.P não pode ser maior que 8 caracteres");
        }

        if(nm_cidade.isBlank()){
            errors.put("basError", "Cidade não pode ser vazio");
        } else if (nm_cidade.length() > 100) {
            errors.put("basError", "Cidade não pode ser maior que 8 caracteres");
        }

        if(nm_estado.isBlank()){
            errors.put("basError", "Estado não pode ser vazio");
        }

        if(nm_rua.isBlank()){
            errors.put("basError", "Rua não pode ser vazio");
        } else if (nm_rua.length() > 100) {
            errors.put("basError", "Rua não pode ser maior que 100 caracteres");
        }

        if(nr_residencia == 0){
            errors.put("basError", "Número residencia não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(ClienteRepository.class, cliente -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (cliente.exists(nr_cpf)) {
                    cliente.update(cliente_insert);
                } else {
                    cliente.insert(cliente_insert);
                }
            }

            final var now = LocalDateTime.now();
            final var count = cliente.count();
            final var clientes = cliente.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new ClienteViewData(clientes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new ClienteViewData(errors, cliente_insert, clientes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Cliente>("cliente/form", response);
        final var nr_cpf = NumberUtils.toInt(request.getParameter("nr_cpf"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, cliente -> {
            renderer.render(cliente.findByCliente(nr_cpf));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var bar = Integer.parseInt(request.getParameter("bar"));

        JDBIConnection.instance().withExtension(FooRepository.class, cliente -> {
            cliente.delete(bar);
            loadFullPage(request, response);
            return null;
        });
    }
}
