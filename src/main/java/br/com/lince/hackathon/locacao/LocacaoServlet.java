package br.com.lince.hackathon.locacao;

//import br.com.lince.hackathon.Veiculo.Veiculo;
//import br.com.lince.hackathon.Veiculo.VeiculoRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

//@WebServlet("/locacao/*")
public class LocacaoServlet extends HttpServlet {
//    private static final int PAGE_SIZE = 10;
//
//    private static final Logger logger = Logger.getLogger(LocacaoServlet.class.getName());
//
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
//
//        switch (requestPath) {
//            case "":
//            case "/":
//                loadFullPage(request, response);
//                break;
//
//            case "/edit":
//                loadFormEditLocacao(request, response);
//                break;
//
//            case "/delete":
//                deleteLocacao(request, response);
//                break;
//
//            default:
//                response.getWriter().write("Not found: " + requestPath);
//        }
//    }
//
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";
//        System.out.println(requestPath);
//
//        if (requestPath.isBlank()) {
//            loadFullPage(request, response);
//        } else if (requestPath.equals("/upsert")) {
//            insertOrUpdateLocacao(request, response);
//        } else {
//            response.getWriter().write("Not found: " + requestPath);
//        }
//    }
//
//    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
//        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
//
//        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            final var now = LocalDateTime.now();
//            final var count = dao.count();
//            final var locacoes = dao.selectPage(page, PAGE_SIZE);

//            renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));
//
//            return null;
//        });
//    }
//
//    private void insertOrUpdateLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var renderer = new TemplateRenderer<LocacaoViewData>("locacao/page", response);
//        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
//
//        final var id = NumberUtils.toInt(request.getParameter("id"));
//        final var clienteId = NumberUtils.toInt(request.getParameter("clienteId"));
//        final var gerenteId = NumberUtils.toInt(request.getParameter("gerenteId"));
//        final var veiculoId = NumberUtils.toInt(request.getParameter("veiculoId"));
//        final var dataInicio = LocalDate.parse(request.getParameter("dataInicio"));
//        final var dataEntrega = LocalDate.parse(request.getParameter("dataEntrega"));
//        final var valorDiaria = Double.parseDouble(request.getParameter("valorDiaria"));
//        final var percentualComissao = Double.parseDouble(request.getParameter("percentualComissao"));
//        final var valorTotalPago = Double.parseDouble(request.getParameter("valorTotalPago"));
//        final var dataPagamento = LocalDate.parse(request.getParameter("dataPagamento"));
//
//        JDBIConnection.instance().withExtension(ClienteRepository.class, clienteDao -> {
//            Cliente cliente = clienteDao.findById(clienteId);
//
//            JDBIConnection.instance().withExtension(GerenteRepository.class, gerenteDao -> {
//                Gerente gerente = gerenteDao.findById(gerenteId);
//
//                JDBIConnection.instance().withExtension(VeiculoRepository.class, veiculoDao -> {
//                    Veiculo veiculo = veiculoDao.findById(veiculoId);
//
//                    final var locacao = new Locacao(id, cliente, gerente, veiculo, dataInicio, dataEntrega, valorDiaria, percentualComissao, valorTotalPago, dataPagamento);
//                    final var errors = new HashMap<String, String>();
//
//                    validateFields(locacao, errors);
//
//                    JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//                        if (errors.isEmpty()) {
//                            if (dao.exists(id)) {
//                                dao.update(locacao);
//                            } else {
//                                dao.insert(locacao);
//                            }
//                        }
//
//                        final var now = LocalDateTime.now();
//                        final var count = dao.count();
//                        final var locacoes = dao.selectPage(page, PAGE_SIZE);
//
//                        if (errors.isEmpty()) {
//                            renderer.render(new LocacaoViewData(locacoes, now, page, PAGE_SIZE, count));
//                        } else {
//                            renderer.render(new LocacaoViewData(errors, locacao, locacoes, now, page, PAGE_SIZE, count));
//                        }
//
//                        return null;
//                    });
//
//                    return null;
//                });
//
//                return null;
//            });
//
//            return null;
//        });
//    }
//
//    private void validateFields(Locacao locacao, HashMap<String, String> errors) {
//        if (locacao.getCliente() == null) {
//            errors.put("clienteError", "Cliente não pode ser vazio");
//        }
//        if (locacao.getGerente() == null) {
//            errors.put("gerenteError", "Gerente não pode ser vazio");
//        }
//        if (locacao.getVeiculo() == null) {
//            errors.put("veiculoError", "Veículo não pode ser vazio");
//        }
//        if (locacao.getDataInicio() == null) {
//            errors.put("dataInicioError", "Data de início não pode ser vazia");
//        }
//        if (locacao.getDataEntrega() == null) {
//            errors.put("dataEntregaError", "Data de entrega não pode ser vazia");
//        }
//        if (locacao.getValorDiaria() <= 0) {
//            errors.put("valorDiariaError", "Valor da diária deve ser maior que zero");
//        }
//        if (locacao.getPercentualComissao() < 0) {
//            errors.put("percentualComissaoError", "Percentual de comissão deve ser maior ou igual a zero");
//        }
//        if (locacao.getValorTotalPago() <= 0) {
//            errors.put("valorTotalPagoError", "Valor total pago deve ser maior que zero");
//        }
//        if (locacao.getDataPagamento() == null) {
//            errors.put("dataPagamentoError", "Data de pagamento não pode ser vazia");
//        }
//    }
//
//    private void loadFormEditLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var renderer = new TemplateRenderer<Locacao>("locacao/form", response);
//        final var id = NumberUtils.toInt(request.getParameter("id"));
//
//        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            renderer.render(dao.findById(id));
//            return null;
//        });
//    }
//
//    private void deleteLocacao(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final var id = NumberUtils.toInt(request.getParameter("id"));
//
//        JDBIConnection.instance().withExtension(LocacaoRepository.class, dao -> {
//            dao.delete(id);
//            loadFullPage(request, response);
//            return null;
//        });
//    }
}