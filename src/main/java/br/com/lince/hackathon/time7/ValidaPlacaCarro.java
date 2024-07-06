package br.com.lince.hackathon.time7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaPlacaCarro {
    private static final String PLACA_REGEX =
            "^[A-Z]{3}\\d[a-zA-Z0-9]\\d{2}$";

    // Função para validar o endereço de e-mail
    public static boolean isPlacaValid(String placa) {
        // Compilar o padrão de regex
        Pattern pattern = Pattern.compile(PLACA_REGEX);
        if (placa == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(placa);
        return matcher.matches();
    }
}