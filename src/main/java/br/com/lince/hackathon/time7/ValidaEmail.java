package br.com.lince.hackathon.time7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaEmail {

    // Expressão regular para validar endereço de e-mail
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Função para validar o endereço de e-mail
    public static boolean isValidEmail(String email) {
        // Compilar o padrão de regex
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
