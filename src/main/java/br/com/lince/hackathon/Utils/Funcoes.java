package br.com.lince.hackathon.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Funcoes {
    public static int inverteData(String data) {
        String dataString = data;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sd2 = new SimpleDateFormat("dd/MM/yyyy");
        int dataFinal = 0;

        try{
            Date dataFormatada = sd2.parse(dataString);
            dataFinal = Integer.parseInt(sdf.format(dataFormatada));
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

        return dataFinal;
    }

    public static String formataData(int dataInvertida) {
        String dataString = String.valueOf(dataInvertida);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sd2 = new SimpleDateFormat("dd/MM/yyyy");
        String dataFinal = "";

        try{
            Date dataFormatada = sdf.parse(dataString);
             dataFinal = sd2.format(dataFormatada);
            System.out.println(dataFinal);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

        return dataFinal;
    }

    public static boolean validaCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        try{
            Long.parseLong(cpf);
        } catch(NumberFormatException e){
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            d1 = d1 + (11 - nCount) * digitoCPF;
            d2 = d2 + (12 - nCount) * digitoCPF;
        };

        resto = (d1 % 11);

        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;

        resto = (d2 % 11);

        if (resto < 2) {
            digito2 = 0;
        }else{
            digito2 = 11 - resto;
        }

        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }

    public static boolean validaEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static long formataCelular(String telefone) {
        if(!telefone.isBlank()){
            try{
                return Long.parseLong(telefone.replace(" ", "").replaceAll("[^0-9]",""));
            }catch(NumberFormatException e){
                return 0;
            }
        }else{
            return 0;
        }
    }

    public static String formatarCPF(long cpf) {
        String cpfAlfa = String.valueOf(cpf);
        return cpfAlfa.substring(0,3)+"."+cpfAlfa.substring(3,6)+"."+cpfAlfa.substring(6,9)+"-"+cpfAlfa.substring(9,11);
    }

    public static String formatarTelefone(long numero) {
        StringBuilder sb = new StringBuilder(String.valueOf(numero));
        sb.insert(0, "(");
        sb.insert(3, ")");
        sb.insert(8, "-");
        return sb.toString();
    }
}
