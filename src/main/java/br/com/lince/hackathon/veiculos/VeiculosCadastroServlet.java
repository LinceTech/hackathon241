package br.com.lince.hackathon.veiculos;

import br.com.lince.hackathon.standard.JDBIConnection;
import br.com.lince.hackathon.standard.TemplateRenderer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/veiculosCadastro/*")
public class VeiculosCadastroServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(VeiculosCadastroServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";

        switch (requestPath) {
            case "":
                loadFormCadastroVeiculos(request, response);
                break;
            default:
                response.getWriter().write("Not found : " + requestPath);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var requestPath = request.getPathInfo() != null ? request.getPathInfo() : "";


        if (requestPath.isBlank()) {
            loadFormCadastroVeiculos(request, response);
        } else if (requestPath.equals("/cadastrar")) {
            salvarVeiculo(request, response);
        } else {
            response.getWriter().write("Not found : " + requestPath);
        }
    }

    private void loadFormCadastroVeiculos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosViewData>("veiculos/veiculosCadastro", response);

        renderer.render(new VeiculosViewData());
    }

    private void salvarVeiculo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var renderer = new TemplateRenderer<VeiculosCadastroViewData>("veiculos/veiculosCadastroForm", response);
        final String cor = request.getParameter("cor");
        final String marca = request.getParameter("marca");
        final String placa = request.getParameter("placa");
        final String modelo = request.getParameter("modelo");
        final String custoDeDiariaS = request.getParameter("custoDeDiaria");
        final String descricaoPromocional = request.getParameter("descricaoPromocional");
        final String tipoDeCombustivelS = request.getParameter("tipoDeCombustivel");
        final String anoDeFabricacaoS = request.getParameter("anoDeFabricacao");
        final var errors = new HashMap<String, String>();

        if (cor.isBlank()) {
            errors.put("cor", "Cor não pode ser vazio");
        } else if (cor.length() > 60) {
            errors.put("cor", "Cor não pode ser maior que 60 caracteres");
        }
        if (marca.isBlank()) {
            errors.put("marca", "Marca não pode ser vazio");
        } else if (marca.length() > 60) {
            errors.put("marca", "Marca não pode ser maior que 60 caracteres");
        }
        if (placa.isBlank()) {
            errors.put("placa", "Placa não pode ser vazia");
        } else if (placa.length() > 8) {
            errors.put("placa", "Placa não pode ser maior que 8 caracteres");
        }
        if (modelo.isBlank()) {
            errors.put("modelo", "Modelo não pode ser vazia");
        } else if (modelo.length() > 60) {
            errors.put("modelo", "Modelo não pode ser maior que 8 caracteres");
        }
        if (descricaoPromocional.isBlank()) {
            errors.put("descricaoPromocional", "Descrição promocional não pode ser vazia");
        } else if (descricaoPromocional.length() > 60) {
            errors.put("descricaoPromocional", "Descrição promocional não pode ser maior que 60 caracteres");
        }
        int tipoDeCombustivel = 0;
        if (tipoDeCombustivelS.isBlank()) {
            errors.put("tipoDeCombustivel", "Tipo de combustível não pode ser vazio");
        }else{
            tipoDeCombustivel = Integer.parseInt(request.getParameter("tipoDeCombustivel"));
            if (tipoDeCombustivel == 0) {
                errors.put("tipoDeCombustivel", "Tipo de combustível não pode ser vazio");
            }
        }
        int anoDeFabricacao = 0;
        if (anoDeFabricacaoS.isBlank()) {
            errors.put("anoDeFabricacao", "Ano de fabricação não pode ser vazia");
        }else{
            anoDeFabricacao = Integer.parseInt(request.getParameter("anoDeFabricacao"));
            if (anoDeFabricacao == 0) {
                errors.put("anoDeFabricacao", "Ano de fabricação não pode ser vazia");
            }
        }
        Float custoDeDiaria = null;
        if (custoDeDiariaS.isBlank()) {
            errors.put("custoDeDiaria", "Custo de diária não pode ser vazio");
        } else {
            custoDeDiaria = Float.parseFloat(custoDeDiariaS);
            if (custoDeDiaria == 0) {
                errors.put("custoDeDiaria", "Custo de diária não pode ser vazio");
            }
        }

        final var veiculos = new Veiculos(cor, marca, placa, modelo, descricaoPromocional, tipoDeCombustivel, anoDeFabricacao, custoDeDiaria);
        switch (tipoDeCombustivel) {
            case 1:
                veiculos.fgAlcool=true;
                break;
            case 2:
                veiculos.fgGasolina=true;
                break;
            case 3:
                veiculos.fgGNV=true;
                break;
            case 4:
                veiculos.fgEletrico=true;
                break;
            default:
                veiculos.semCombustivel=true;
                break;
        }

        JDBIConnection.instance().withExtension(VeiculoRepository.class, dao -> {
            // Verificar se ocorreram erros no formulário
            if (errors.isEmpty()) {
                dao.insert(veiculos);
                renderer.render(new VeiculosCadastroViewData());
            } else {
                renderer.render(new VeiculosCadastroViewData(errors, veiculos));
            }
            return null;
        });
    }
}
