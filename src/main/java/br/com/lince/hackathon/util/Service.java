package br.com.lince.hackathon.util;

import java.time.LocalDate;
import java.time.Period;
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
}
