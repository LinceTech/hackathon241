package br.com.lince.hackathon.locacao;

import br.com.lince.hackathon.Cliente.Cliente;
import br.com.lince.hackathon.Cliente.ClienteRepository;
import br.com.lince.hackathon.Utils.Funcoes;
import br.com.lince.hackathon.gerente.Gerente;
import br.com.lince.hackathon.gerente.GerenteRepository;
import br.com.lince.hackathon.veiculo.Veiculo;
import br.com.lince.hackathon.veiculo.VeiculoRepository;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;
import br.com.lince.hackathon.standard.TemplateRenderer;
import br.com.lince.hackathon.standard.JDBIConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/locacao/*")
public class LocacaoServlet extends HttpServlet {
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(LocacaoServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditLocacao(request, response);
                break;

            case "/delete":
                deleteLocacao(request, response);
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
            insertOrUpdateLocacao(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locacoes = dao.selectPage(page, PAGE_SIZE);

            renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));

            return null;
        });
    }

    private void insertOrUpdateLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);

        final var id = NumberUtils.toInt(request.getParameter("id"), 0);
        final var clienteCpf = NumberUtils.toLong(request.getParameter("clienteCpf"), 0);
        final var gerenteCpf = NumberUtils.toLong(request.getParameter("gerenteCpf"), 0);
        final var veiculoPlaca = request.getParameter("placaVeiculo");
        final var dataInicio = NumberUtils.toInt(request.getParameter("dataInicio"), 0);
        final var dataEntrega = NumberUtils.toInt(request.getParameter("dataEntrega"), 0);
        final var valorDiaria = NumberUtils.toDouble(request.getParameter("valorDiaria").replaceAll(",","."), 0);
        final var percentualComissao = NumberUtils.toDouble(request.getParameter("percentualComissao").replaceAll(",","."), 0);
        final var valorTotalPago = NumberUtils.toDouble(request.getParameter("valorTotalPago").replaceAll(",","."), 0);
        final var dataPagamento = NumberUtils.toInt(request.getParameter("dataPagamento"), 0);



        final var locacao = new Locacao(id, clienteCpf, gerenteCpf, veiculoPlaca, dataInicio,dataEntrega,valorDiaria,percentualComissao,valorTotalPago,dataPagamento);
        final var errors = new HashMap<String, String>();

        if (clienteCpf == 0 ) {
            errors.put("clienteCpfError", "Não pode ser vazio");
        }
        System.out.println("gerenteCpf > " + gerenteCpf);
//        if (gerenteCpf == 0 ) {
//            errors.put("gerenteCpfError", "Não pode ser vazio");
//        }
        if (veiculoPlaca.isBlank()) {
            errors.put("veiculoPlacaError", "Não pode ser vazio");
        }
        if (dataInicio == 0 ) {
            errors.put("dataInicioError", "Não pode ser vazio");
        }
        if (dataEntrega == 0 ) {
            errors.put("dataEntregaError", "Não pode ser vazio");
        }
        if (valorDiaria == 0 ) {
            errors.put("valorDiariaError", "Não pode ser vazio");
        }
        if (percentualComissao == 0 ) {
            errors.put("percentualComissaoError", "Não pode ser vazio");
        }
        if (valorTotalPago == 0 ) {
            errors.put("valorTotalPagoError", "Não pode ser vazio");
        }
        if (dataPagamento == 0 ) {
            errors.put("dataPagamentoError", "Não pode ser vazio");
        }

        System.out.println("errors >:"+errors);

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                System.out.println(id + " <> " + dao.exists(id));
                if (dao.exists(id)) {
                    System.out.println("Passou 2");
                    dao.update(locacao);
                } else {
                    System.out.println("insert 2");
                    dao.insert(locacao);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var locacoes = dao.selectPage(page, PAGE_SIZE);

            if (errors.isEmpty()) {
                renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));
            } else {
                renderer.render(new LocacaoViewData(errors, locacao, locacoes, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    private void loadFormEditLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Locacao>("locacao/form", response);
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);
        System.out.println("entrou form");
        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {

            Locacao g = dao.findById(id);
            System.out.println(g.getClienteCpf());
            System.out.println(g.getGerenteCpf());
            System.out.println(g.getPlacaVeiculo());
            renderer.render(g);
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var id = NumberUtils.toInt(request.getParameter("id"), 0);

        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
            dao.delete(id);
            loadFullPage(request, response);
            return null;
        });
    }
}