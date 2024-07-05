package br.com.lince.hackathon.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Validacao {

    public static boolean isMaior18(LocalDate dt_nascimento) {
        var ldNow = LocalDate.now();
        var chrono = ChronoUnit.YEARS.between(dt_nascimento, ldNow );

        return chrono >= 18;
    }


    public static boolean isCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11)
            return false;

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9)
            digito1 = 0;

        // Verifica o primeiro dígito verificador
        if ((cpf.charAt(9) - '0') != digito1)
            return false;

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9)
            digito2 = 0;

        // Verifica o segundo dígito verificador
        if ((cpf.charAt(10) - '0') != digito2)
            return false;

        // CPF válido
        return true;
    }

}
