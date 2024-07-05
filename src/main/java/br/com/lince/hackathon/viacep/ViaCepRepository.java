package br.com.lince.hackathon.viacep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViaCepRepository {

    public static boolean exist(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep.replace("-", "") + "/json/";

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = inputReader.readLine()) != null) {
                response.append(line);
            }
            inputReader.close();

            // Fechando a conexão
            connection.disconnect();

            String read = response.toString();
            if (read.contains("erro")){
                return false;
            }

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
