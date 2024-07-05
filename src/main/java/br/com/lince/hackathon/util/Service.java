package br.com.lince.hackathon.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    public static boolean validarCPF(String cpf) {
        // Remove caracteres que não são dígitos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, o que invalida o CPF
        boolean todosDigitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }

        // Calcula e compara o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }
        if (Character.getNumericValue(cpf.charAt(9)) != digito1) {
            return false;
        }

        // Calcula e compara o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }
        if (Character.getNumericValue(cpf.charAt(10)) != digito2) {
            return false;
        }

        // Se passou por todas as verificações, o CPF é válido
        return true;
    }

    public static boolean validarEmail(String email) {
        // Expressão regular para validar o formato do email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        // Cria um objeto Matcher com o email fornecido
        Matcher matcher = pattern.matcher(email);

        // Retorna true se o email corresponder à expressão regular
        return matcher.matches();
    }

    public static boolean validarIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(dataNascimento, hoje);
        int idade = periodo.getYears();

        return idade >= 18;
    }

    public static LocalDate verificaData(String data) {
        if (data == null) {
            return LocalDate.now();
        }
        if (data.isBlank()){
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(data);
        }catch (Exception e){
            return LocalDate.now();
        }
    }

    public static List<State> findStates(){
        List<State> states = new ArrayList<>();

        states.add(new State("AC", "Acre"));
        states.add(new State("AL", "Alagoas"));
        states.add(new State("AP", "Amapá"));
        states.add(new State("AM", "Amazonas"));
        states.add(new State("BA", "Bahia"));
        states.add(new State("CE", "Ceará"));
        states.add(new State("DF", "Distrito Federal"));
        states.add(new State("ES", "Espírito Santo"));
        states.add(new State("GO", "Goiás"));
        states.add(new State("MA", "Maranhão"));
        states.add(new State("MT", "Mato Grosso"));
        states.add(new State("MS", "Mato Grosso do Sul"));
        states.add(new State("MG", "Minas Gerais"));
        states.add(new State("PA", "Pará"));
        states.add(new State("PB", "Paraíba"));
        states.add(new State("PR", "Paraná"));
        states.add(new State("PE", "Pernambuco"));
        states.add(new State("PI", "Piauí"));
        states.add(new State("RJ", "Rio de Janeiro"));
        states.add(new State("RN", "Rio Grande do Norte"));
        states.add(new State("RS", "Rio Grande do Sul"));
        states.add(new State("RO", "Rondônia"));
        states.add(new State("RR", "Roraima"));
        states.add(new State("SC", "Santa Catarina"));
        states.add(new State("SP", "São Paulo"));
        states.add(new State("SE", "Sergipe"));
        states.add(new State("TO", "Tocantins"));

        return states;
    }

}
