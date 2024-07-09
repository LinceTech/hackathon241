package br.com.lince.hackathon.utils;

import br.com.lince.hackathon.cliente.ClienteServlet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacao {

    private static final Logger logger = Logger.getLogger(ClienteServlet.class.getName());

    public static boolean isMaior18(LocalDate dt_nascimento) {
        var ldNow = LocalDate.now();
        var chrono = ChronoUnit.YEARS.between(dt_nascimento, ldNow );
        logger.severe(""+chrono);
        return chrono >= 18;
    }


    public static boolean isCpf(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11)) {
            return false;
        }

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

    public static boolean isEmail(String email) {
        // Expressão regular para validar email
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean isNull(Object obj){
        return Objects.isNull(obj);
    }

    public static String formataCpf(String cpf) {
        return  cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9);
    }

    public static String formataData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String formataTelefone(String telefone) {
        return "(" + telefone.substring(0, 2) + ") " +
                telefone.substring(2, 3) + " " +
                telefone.substring(3, 7) + "-" +
                telefone.substring(7);
    }

}
