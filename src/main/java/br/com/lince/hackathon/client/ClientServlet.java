package br.com.lince.hackathon.client;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/clientes/*")
public class ClientServlet extends HttpServlet {

    private static final int PAGE_SIZE = 5;

    private static final Logger logger = Logger.getLogger(ClientServlet.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
//                loadFormEditFoo(request, response);
                break;

            case "/delete":
//                deleteFoo(request, response);
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

    /*CARREGA VALORES*/
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientViewData>("client/pageClient", response);
        final var page = NumberUtils.toInt(request.getParameter("pageClient"), 0);

        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.countClient();
            final var clients = dao.selectPageClient(page, PAGE_SIZE);

            renderer.render(new ClientViewData(clients, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    /*ATUALIZA VALORES*/
    private void insertOrUpdateFoo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<ClientViewData>("client/pageClient", response);
        final var page = NumberUtils.toInt(request.getParameter("pageClient"), 0);
        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date_nascimento = new Date();
        try {
            date_nascimento = formatter.parse(request.getParameter("data_nascimento"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final var data_nascimento = request.getParameter("data_nascimento");
        final var telefone = request.getParameter("telefone");
        final var email = request.getParameter("email");
        final var cep = request.getParameter("cep");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var bairro = request.getParameter("bairro");
        final var rua = request.getParameter("rua");
        int numero = 0;
        try {
            numero = Integer.parseInt(request.getParameter("numero"));
        }catch (Exception e){
            numero = 0;
        }
        final var client = new Client(0, numero, nome, date_nascimento, cpf,telefone,email,cep,estado,bairro,cidade,rua);
        final var errors = new HashMap<String, String>();

        //NOME
//        if (nome.isBlank()) {
//            errors.put("nome", "Não pode ser vazio");
//        } else if (nome.length() > 255) {
//            errors.put("nome", "Não pode ser maior que 255 caracteres");
//        }
//        //CPF
//        if (cpf.isBlank()) {
//            errors.put("nome", "Não pode ser vazio");
//        } else if (cpf.length() > 11) {
//            errors.put("nome", "Não pode ser maior que 255 caracteres");
//        }
//        //data_nascimento
//        if (data_nascimento.isBlank()) {
//            errors.put("data_nascimento", "Não pode ser vazio");
//        }
//        //TELEFONE
//        if (telefone.isBlank()) {
//            errors.put("telefone", "Não pode ser vazio");
//        } else if (cpf.length() > 20) {
//            errors.put("telefone", "Não pode ser maior que 20 caracteres");
//        }
//        //email
//        if (telefone.isBlank()) {
//            errors.put("email", "Não pode ser vazio");
//        } else if (cpf.length() > 255) {
//            errors.put("email", "Não pode ser maior que 255 caracteres");
//        }
//        //cep
//        if (telefone.isBlank()) {
//            errors.put("cep", "Não pode ser vazio");
//        } else if (cpf.length() > 9) {
//            errors.put("cep", "Não pode ser maior que 9 caracteres");
//        }
//        //cidade
//        if (telefone.isBlank()) {
//            errors.put("cidade", "Não pode ser vazio");
//        } else if (cpf.length() > 255) {
//            errors.put("cidade", "Não pode ser maior que 255 caracteres");
//        }
//        //estado
//        if (telefone.isBlank()) {
//            errors.put("estado", "Não pode ser vazio");
//        } else if (cpf.length() > 2) {
//            errors.put("cidade", "Não pode ser maior que 255 caracteres");
//        }
//        //bairro
//        if (telefone.isBlank()) {
//            errors.put("bairro", "Não pode ser vazio");
//        } else if (cpf.length() > 255) {
//            errors.put("bairro", "Não pode ser maior que 255 caracteres");
//        }
//        //rua
//        if (rua.isBlank()) {
//            errors.put("rua", "Não pode ser vazio");
//        } else if (telefone.length() > 255) {
//            errors.put("rua", "Não pode ser maior que 255 caracteres");
//        }
//        //numero
//        if (telefone.isBlank()) {
//            errors.put("numero", "Não pode ser vazio");
//        }


        JDBIConnection.instance().withExtension(ClientRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.existsClient(client.getId())) {
                    dao.updateClientById(client);
                } else {
                    dao.insertClient(client);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.countClient();
            final var clientes = dao.selectPageClient(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new ClientViewData(clientes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new ClientViewData(errors, client, clientes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }
}
