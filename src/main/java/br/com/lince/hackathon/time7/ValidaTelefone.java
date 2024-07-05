package br.com.lince.hackathon.time7;

public class ValidaTelefone {

    public static boolean validaFone(String telefone) {

        // Expressão regular para validar números de telefone
        // Aceita números com 8 ou 9 dígitos, opcionalmente começando com 0 e com DDD opcional
        String telefoneRegex = "^(0?[1-9]{2})?\\d{8,9}$";

        // Verifica se o telefone corresponde à expressão regular
        return telefone.matches(telefoneRegex);
    }
}
