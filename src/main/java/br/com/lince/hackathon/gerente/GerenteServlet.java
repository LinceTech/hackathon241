package br.com.lince.hackathon.gerente;

import br.com.lince.hackathon.Utils.Funcoes;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.gerente.GerenteRepository;
import br.com.lince.hackathon.gerente.GerenteServlet;
import br.com.lince.hackathon.gerente.GerenteViewData;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/gerente/*")
public class GerenteServlet  extends HttpServlet {
    private static final int PAGE_SIZE = 15;

    private static final Logger logger = Logger.getLogger(GerenteServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditGerente(request, response);
                break;

            case "/delete":
                deleteGerente(request, response);
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
            insertOrUpdateGerente(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void insertOrUpdateGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<GerenteViewData>("gerente/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var nome = request.getParameter("nome");
        final var cpf = request.getParameter("cpf").replace(".", "").replace("-","");
        final var telefone = Funcoes.formataCelular(request.getParameter("telefone"));
        final var email = request.getParameter("email");
        final var cidade = request.getParameter("cidade");
        final var estado = request.getParameter("estado");
        final var percentualComissao = NumberUtils.toDouble(request.getParameter("percentualComissao").replaceAll(",","."), 0);
        System.out.println(request.getParameter("dataContratacao"));
        final var dataContratacao = Funcoes.inverteData(request.getParameter("dataContratacao"));
        final var cpfNumerico = NumberUtils.toLong(cpf.replace(".","").replace("-",""));

        System.out.println(dataContratacao + "DATAAAAAA");

        final var gerente = new Gerente(nome, cpfNumerico, telefone, email, cidade, estado, percentualComissao, dataContratacao, "","","");
        final var errors = new HashMap<String, String>();


        if (nome.isBlank()) {
            errors.put("nomeError", "Não pode ser vazio");
        }

        if (cpf.isBlank()) {
            errors.put("cpfError", "Não pode ser vazio");
        }else if(Funcoes.validaCpf(cpf)){
            errors.put("cpfError", "Inválido");
        }

        if (telefone == 0) {
            errors.put("telefoneError", "Não pode ser vazio");
        }
        if (email.isBlank()) {
            errors.put("emailError", "Não pode ser vazio");
        }else if(Funcoes.validaEmail(email) == false){
            errors.put("emailError", "Inválido");
        }

        if (cidade.isBlank()) {
            errors.put("cidadeError", "Não pode ser vazio");
        }
        if (estado.isBlank()) {
            errors.put("estadoError", "Não pode ser vazio");
        }
        if (percentualComissao == 0) {
            errors.put("percentualComissaoError", "Não pode ser vazio");
        }else if(percentualComissao > 25){
            errors.put("percentualComissaoError", "Nao pode ser maior do que 25%!");
        }
        if (dataContratacao == 0) {
            errors.put("dataContratacaoError", "Não pode ser vazio");
        }

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cpfNumerico)) {
                    dao.update(gerente);
                } else {
                    dao.insert(gerente);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var gerentes = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new GerenteViewData(gerentes, now, page, PAGE_SIZE, count));
            } else {
                Gerente g = dao.findByCpf(cpfNumerico);
                g.setDataContratacaoAlfa(Funcoes.formataData(g.getDataContratacao()));
                g.setCpfAlfa(Funcoes.formatarCPF(g.getCpf()));
                g.setTelefoneAlfa(Funcoes.formatarTelefone(g.getTelefone()));
                renderer.render(new GerenteViewData(errors, g, gerentes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    private void loadFormEditGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Gerente>("gerente/form", response);
        final var cpf = NumberUtils.toLong(request.getParameter("cpf"), 0);

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            Gerente g = dao.findByCpf(cpf);
            g.setDataContratacaoAlfa(Funcoes.formataData(g.getDataContratacao()));
            g.setCpfAlfa(Funcoes.formatarCPF(g.getCpf()));
            g.setTelefoneAlfa(Funcoes.formatarTelefone(g.getTelefone()));
            renderer.render(g);
            return null;
        });
    }

    private void deleteGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var cpf = Integer.parseInt(request.getParameter("cpf"));

        JDBIConnection.instance().withExtension(GerenteRepository.class, dao -> {
            dao.delete(cpf);
            loadFullPage(request, response);
            return null;
        });
    }
}
