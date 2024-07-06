package br.com.lince.hackathon.time7;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Modelos {
    static String webService = "https://fipe.parallelum.com.br/api/v2/cars/brands";
    static int codigoSucesso = 200;

    public static String buscaModeloCode(String marca, String code) throws IOException {
        if (marca.isEmpty() || code.isBlank()) { return ""; }
        String urlParaChamada = webService + "/" + marca + "/models";

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            JsonArray myjson = new Gson().fromJson(resposta, JsonArray.class);
            String name = "";
            for (int i = 0; i < myjson.size(); i++) {
                JsonObject myobj = myjson.get(i).getAsJsonObject();
                if (Objects.equals(myobj.get("code").getAsString(), code)) {
                    name = myobj.get("name").getAsString();
                    break;
                }
            }

            return name;
        } catch (Exception e) {
            throw new IOException("ERRO: " + e);
        }
    }

    public static List<HashMap<String, String>> buscaModelos(String marca) throws IOException {
        String urlParaChamada = webService + "/" + marca + "/models";

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            JsonArray myjson = new Gson().fromJson(resposta, JsonArray.class);
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < myjson.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                myjson.get(i);
                JsonObject myobj = myjson.get(i).getAsJsonObject();
                map.put("code", myobj.get("code").getAsString());
                map.put("name", myobj.get("name").getAsString());
                list.add(map);
            }

            return list;
        } catch (Exception e) {
            throw new IOException("ERRO: " + e);
        }
    }
}
