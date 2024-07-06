package br.com.lince.hackathon.clientes;


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
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/clientesCadastro/*")
public class ClienteCadastroServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(ClienteCadastroServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/cadastrar":
                formClient(request, response);
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
        } else if (requestPath.equals("/cadastrar")) {
            insertOrUpdateCliente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de clientes carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("clientes/clienteCadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            List<Cliente> clientes = dao.getAll();
            renderer.render(new ClientesViewData(clientes, new Cliente(),new HashMap<String, String>()));

            return null;
        });
    }
    /*
     * Trata a requisição para retorna a página de clientes carregada com todos os dados
     */
    private void loadListaClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("clientes/listClientes", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        var nome = request.getParameter("nome");
        var cidade = request.getParameter("cidade");
        var data_nascimento = request.getParameter("data_nascimento");
        final var clienteFiltro = new ClienteFiltro(nome, cidade, data_nascimento);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            final var count = dao.countFilter(clienteFiltro);
            final var clientes = dao.selectFilterPage(page, PAGE_SIZE, clienteFiltro,"nome");

            renderer.render(new ClientesViewData(clientes, page, PAGE_SIZE, count, clienteFiltro));
            return null;
        });
    }
    /*
     * Trata a requisição para retorna a página de clientes carregada com todos os dados
     */
    private void formClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientesViewData>("clientes/clienteCadastro", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            List<Cliente> clientes = dao.getAll();
            renderer.render(new ClientesViewData(clientes, new Cliente(),new HashMap<String, String>()));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um cliente, e retorna página atualizada
     */
    private void insertOrUpdateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var renderer = new TemplateRenderer<ClientesViewData>("clientes/clienteCadastro", response);
        var id = NumberUtils.toInt(request.getParameter("id"), 0);
        var nome = request.getParameter("nome");
        var cpf = request.getParameter("cpf");
        LocalDate data_nascimento = null;
        int idade = 0;
        var telefone = request.getParameter("telefone");
        var email = request.getParameter("email");
        var cep = request.getParameter("cep");
        var cidade = request.getParameter("cidade");
        var estado = request.getParameter("estado");
        var bairro = request.getParameter("bairro");
        var rua = request.getParameter("rua");

        HashMap<String, String> errors = new HashMap<String, String>();
        HashMap<String, String> success = new HashMap<String, String>();

        var numero = 0;

        if (request.getParameter("numero") != null ){
            try{
                numero = Integer.parseInt(request.getParameter("numero"));
                if (numero == 0){
                    errors.put("numeroErro","Número inválido");
                }
            } catch (Exception e){
            }
        }


        if (nome.isBlank()) {
            errors.put("nomeErro", "Nome deve ser informado");
        }
        if (cpf.isBlank()) {
            errors.put("cpfErro", "CPF deve ser informado");
        } else if (cpf.length() != 14) {
            errors.put("cpfErro", "CPF inválido");
        }
        try {
            data_nascimento = LocalDate.parse(request.getParameter("data_nascimento").trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //Verificação de idade
            LocalDate hoje = LocalDate.now();
            Period periodo = Period.between(data_nascimento, hoje);
            idade = periodo.getYears();
        } catch (java.time.format.DateTimeParseException e) {
            errors.put("dataErro", "Data de nascimento deve ser iformada");

        }
        if (idade < 18) {
            errors.put("idadeErro", "A idade mínima é 18 anos");
        }
        if (telefone.isBlank()){
            errors.put("telefoneErro","Telefone deve ser informado");
        }
        if (email.isBlank()){
            errors.put("emailErro","Email deve ser informado");
        }
        if (cep.isBlank()){
            errors.put("cepErro","CEP deve ser informado");
        }
        if (cidade.isBlank()){
            errors.put("cidadeErro","Cidade deve ser informado");
        }
        if (estado.isBlank()){
            errors.put("estadoErro","Estado deve ser informado");
        }
        if (bairro.isBlank()){
            errors.put("bairroErro","Bairro deve ser informado");
        }
        if (rua.isBlank()){
            errors.put("ruaErro","Rua deve ser informado");
        }



        final var cliente = new Cliente(id,nome, cpf,data_nascimento,telefone,email,cep,cidade,estado,bairro,rua,numero);

        logger.log(Level.INFO, "cliente -> " + cliente.getNome());
        logger.log(Level.INFO, "erros ->"+errors.toString());
        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
        // Verificar se ocorreram erros no formulário
        if (dao.findByCPF(cpf,id)){
            errors.put("cpfErro","CPF já cadastrado");
        }
        if (dao.findByEmail(cpf,id)){
            errors.put("emailErro","Email já cadastrado");
        }
        if (errors.isEmpty()) {
            if (dao.exists(id)) {
                Cliente clienteVerificacao =  dao.findById(cliente.getId());
                if(!clienteVerificacao.getCpf().equals(cpf)){
                    errors.put("cpfErro","CPF não pode ser diferente do cadastro original");
                    renderer.render(new ClientesViewData(errors, cliente));
                }
                if (errors.isEmpty()) {
                    success.put("message", "Cliente atualizado com sucesso");
                    dao.update(cliente);
                    renderer.render(new ClientesViewData(success, cliente));

                }
            }else{
               dao.insert(cliente);
               success.put("message", "Cliente criado com sucesso");
               renderer.render(new ClientesViewData(success, cliente));
            }
        }else {
            renderer.render(new ClientesViewData(errors, cliente));
        }

//            final var now = LocalDateTime.now();
//            final var count = dao.count();
//            final var clientes = dao.selectPage(page, PAGE_SIZE);

//            if (errors.isEmpty()) {
//                renderer.render(new ClientesViewData(clientes, now, page, PAGE_SIZE, count));
//            } else {
//                renderer.render(new ClientesViewData(errors, cliente, clientes, now, page, PAGE_SIZE, count));
//            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Cliente>("clientes/clienteCadastro", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(ClienteRepository.class, dao -> {
            renderer.render(dao.findById(id));
            return null;
        });
    }

}
