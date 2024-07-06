package br.com.lince.hackathon.Utils;

public class Funcoes {
    public static int inverteData(String data) {
        String[] datas = data.split("/");
        int dataInvertida = 0;

        if(data.isEmpty())
        try {
            dataInvertida = Integer.parseInt(datas[2] + datas[1] + datas[0]);

        } catch (NumberFormatException e) {
            return 0;
        }
        return  dataInvertida;
    }
}
