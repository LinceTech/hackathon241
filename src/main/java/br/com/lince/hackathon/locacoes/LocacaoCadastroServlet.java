package br.com.lince.hackathon.locacoes;

import br.com.lince.hackathon.clientes.*;
import br.com.lince.hackathon.gerentes.GerenteRepository;
import br.com.lince.hackathon.gerentes.Gerentes;
import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.veiculos.VeiculoRepository;
import br.com.lince.hackathon.veiculos.Veiculos;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/locacoesCadastro/*")
public class LocacaoCadastroServlet extends HttpServlet {


        private static final int PAGE_SIZE = 15;

        private static final Logger logger = Logger.getLogger(br.com.lince.hackathon.locacoes.LocacaoCadastroServlet.class.getName());

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

            switch (requestPath) {
                case "":
                case "/cadastrar":
                    formLocacao(request, response);
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

        private void formLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
            final var renderer = new TemplateRenderer<LocacaoViewData>("locacoes/LocacaoCadastroForm", response);
            final var page = NumberUtils.toInt(request.getParameter("page"), 0);

            JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
                final var now = LocalDateTime.now();
                final var count = dao.count();
                List<Locacao> locacoes = dao.getAll();
                renderer.render(new LocacaoViewData(new HashMap<String, String>(), new Locacao()));

                return null;
            });
        }

        /*
         * Trata a requisição para inserir ou atualizar um cliente, e retorna página atualizada
         */
        private void insertOrUpdateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
            var renderer = new TemplateRenderer<LocacaoViewData>("locacoes/locacaoCadastro", response);
            var id = NumberUtils.toInt(request.getParameter("id"), 0);
            HashMap<String,String> errors = new HashMap<>();
            // Acessando o repositório de Clientes

            AtomicReference<Cliente> cliente = new AtomicReference<>();
            JDBIConnection.instance().withExtension(ClienteRepository.class, clienteDao -> {
                cliente.set(clienteDao.findById(NumberUtils.toInt(request.getParameter("id_cliente"))));
                return null;
            });

            AtomicReference<Gerentes> gerente = new AtomicReference<>();
            JDBIConnection.instance().withExtension(GerenteRepository.class, gerenteDao -> {
                gerente.set(gerenteDao.findById(NumberUtils.toInt(request.getParameter("id_gerente"))));
                return null;
            });
            AtomicReference<Veiculos> veiculo = new AtomicReference<>();
            JDBIConnection.instance().withExtension(VeiculoRepository.class, veiculoDao -> {
                veiculo.set(veiculoDao.findById(NumberUtils.toInt(request.getParameter("id_veiculo"))));
                return null;
            });

            LocalDate data_inicio = null;
            try {
                data_inicio = LocalDate.parse(request.getParameter("data_inicio").trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (java.time.format.DateTimeParseException e) {
                errors.put("dataInicioErro", "Data de início deve ser iformada");
            }
            LocalDate data_entrega = null;
            try {
                data_entrega = LocalDate.parse(request.getParameter("data_final").trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (java.time.format.DateTimeParseException e) {
                errors.put("dataInicioErro", "Data de entrega deve ser iformada");
            }

            final var locacao = new Locacao();


            renderer.render(new LocacaoViewData(errors,locacao));



    }

}
