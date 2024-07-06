package br.com.lince.hackathon.veiculo;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;
import com.github.jknack.handlebars.internal.lang3.math.NumberUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Servlet que responde a todas as ações relacionadas ao gerenciamento de foos
 */
@WebServlet("/veiculo/*")
public class veiculoServlet extends HttpServlet {
    /*
     * O número de itens na paginação desta tela
     */
    private static final int PAGE_SIZE = 15;

    /*
     * Logger padrão do servlet
     */
    private static final Logger logger = Logger.getLogger(veiculoServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
            case "/":
                loadFullPage(request, response);
                break;

            case "/edit":
                loadFormEditVeiculo(request, response);
                break;

            case "/delete":
                deleteVeiculo(request, response);
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
            insertOrUpdateVeiculo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    /*
     * Trata a requisição para retorna a página de foos carregada com todos os dados
     */
    private void loadFullPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<veiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        this.findMarcas("cars");
        var orderBy = "CDVEICULO:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDVEICULO:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        JDBIConnection.instance().withExtension(veiculoRepository.class, dao -> {
            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var veiculos = dao.selectPage(page, PAGE_SIZE, odb);

            renderer.render(new veiculoViewData(veiculos, now, page, PAGE_SIZE, count, findMarcas("cars")));

            return null;
        });
    }

    /*
     * Trata a requisição para inserir ou atualizar um foo, e retorna página atualizada
     */
    private void insertOrUpdateVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<veiculoViewData>("veiculo/page", response);
        final var page = NumberUtils.toInt(request.getParameter("page"), 0);
        var orderBy = "CDVEICULO:ASC";
        if(request.getParameter("orderBy") != null){
            orderBy = request.getParameter("orderBy").trim().equals("") ? "CDVEICULO:ASC" : request.getParameter("orderBy");
        }
        final var odb = orderBy.replace(":", " "). replace("ASC" , "");
        final var cdveiculo = NumberUtils.toInt(request.getParameter("CDVEICULO"), 0);
        final var indativo = 1;
        final var placa = request.getParameter("PLACA");
        final var cdcor = NumberUtils.toInt(request.getParameter("CDCOR"),0);
        final var anofabricacao = LocalDate.parse(request.getParameter("ANOFABRICACAO"), DateTimeFormatter.ISO_LOCAL_DATE);
        final var valdiario = NumberUtils.toDouble(request.getParameter("VALDIARIO"),0.0);
        final var indpromocao = NumberUtils.toInt(request.getParameter("INDPROMOCAO"), 0);
        final var despromocao = request.getParameter("DESPROMOCAO");
        final var cdcombustivel = NumberUtils.toInt(request.getParameter("CDCOMBUSTIVEL"), 0);
        final var valpromocao = NumberUtils.toDouble(request.getParameter("VALPROMOCAO"), 0.0);
        final var cdmarca = NumberUtils.toInt(request.getParameter("CDMARCA"), 0);
        final var cdmodelo = NumberUtils.toInt(request.getParameter("CDMODELO"), 0);
        final var veiculo = new Veiculo(cdveiculo, indativo, placa, cdcor, anofabricacao, valdiario, indpromocao, despromocao, cdcombustivel, valpromocao, cdmarca, cdmodelo);
        final var errors = new HashMap<String, String>();

        if (cdveiculo == 0) {
            errors.put("Código do veiculo", "Não pode ser vazio");
        } else if (cdveiculo > 999999999) {
            errors.put("Código do veiculo", "Não pode ser maior que 9 caracteres");
        }

        if (placa.length() == 0) {
            errors.put("Placa", "Não pode ser vazio");
        } else if (placa.length() > 20) {
            errors.put("Placa", "Não pode ser maior que 9 caracteres");
        }

        if (anofabricacao == null) {
            errors.put("Ano de fabricação", "Não pode ser vazio");
        }

        if (valdiario == 0) {
            errors.put("Valor diário", "Não pode ser vazio");
        } else if (valdiario > 999999999) {
            errors.put("Valor diário", "Não pode ser maior que 9 caracteres");
        }

        if (despromocao.length() == 0) {
            errors.put("Descrição da promoção", "Não pode ser vazio");
        } else if (despromocao.length() > 100) {
            errors.put("Descrição", "Não pode ser maior que 9 caracteres");
        }

        if (valpromocao == 0) {
            errors.put("Valor promoção", "Não pode ser vazio");
        } else if (valpromocao > 999999999) {
            errors.put("Valor promoção", "Não pode ser maior que 9 caracteres");
        }


        log(errors.toString());
        JDBIConnection.instance().withExtension(veiculoRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                if (dao.exists(cdveiculo)) {
                    dao.update(veiculo);
                } else {
                    dao.insert(veiculo);
                }
            }

            final var now = LocalDateTime.now();
            final var count = dao.count();
            final var veiculos = dao.selectPage(page, PAGE_SIZE, odb);

            if (errors.isEmpty()) {
                renderer.render(new veiculoViewData(veiculos, now, page, PAGE_SIZE, count, findMarcas("cars")));
            } else {
                renderer.render(new veiculoViewData(errors, veiculo, veiculos, now, page, PAGE_SIZE, count));
            }

            return null;
        });
    }

    /*
     * Trata a requisição para alimentar o formulário de cadastro ou edição de foos
     */
    private void loadFormEditVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<Veiculo>("veiculo/form", response);
        final var veiculo = NumberUtils.toInt(request.getParameter("CDVEICULO"), 0);

        JDBIConnection.instance().withExtension(veiculoRepository.class, dao -> {
            renderer.render(dao.findByBar(veiculo));
            return null;
        });
    }

    /*
     * Trata a requisição de exclusão de foos
     */
    private void deleteVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log(request.getParameter("CDVEICULO"));

        final var veiculo = Integer.parseInt(request.getParameter("CDVEICULO"));

        JDBIConnection.instance().withExtension(veiculoRepository.class, dao -> {
            dao.delete(veiculo);
            loadFullPage(request, response);
            return null;
        });
    }

    private List<Map> findMarcas(String tipo) throws IOException{
        List<Map> lista = new ArrayList<>();
        URL brandURL = new URL("https://fipe.parallelum.com.br/api/v2/" + tipo + "/brands");
        HttpURLConnection urlcon = (HttpURLConnection) brandURL.openConnection();
        urlcon.setDoInput(true);
        urlcon.setRequestMethod("GET");
        urlcon.setRequestProperty("Content-Type", "application/json");
        urlcon.setRequestProperty("charset", "utf-8");
        urlcon.setRequestProperty("X-Subscription-Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJiNmUzMDI3NS05NjE5LTQ1ZTgtYjllMi1iOTM2Y2IzODI3ZDkiLCJlbWFpbCI6Imltdml0b29yQGdtYWlsLmNvbSIsImlhdCI6MTcyMDI5MTAyMn0.WqG47CVUHQP8w5RAkCdQNxbqm_LkWstGocFj78CNSy8");
        BufferedReader br = null;
        if(urlcon.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(urlcon.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String cl;
        while ((cl = br.readLine()) != null){
            sb.append(cl);
        }
        log(sb.toString());
        log("SIZE->" + sb.toString().length());
        JsonParser parser = new JsonParser();
        JsonArray jsonA = (JsonArray) parser.parse(sb.toString());
//        log(jsonA.toString());
        for(var json : jsonA){
//            log("->" + json.toString());
            Map<String, Object> dynamicObject = new HashMap<>();
            JsonObject jso = json.getAsJsonObject();
//            Marca marca = new Marca(jso.get("name"), jso.get("code"));
//            Marca marca = new Marca();
//            marca.setName(jso.get("name"));
//            marca.setCode(jso.get("code"));
//            lista.add(marca);

            dynamicObject.put("name", jso.get("name"));
            dynamicObject.put("code", jso.get("code"));
            lista.add(dynamicObject);
        }
        log(lista.toString());
        //sb -> JSONObject
        return lista;
    }

}
