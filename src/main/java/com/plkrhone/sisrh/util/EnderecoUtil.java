package com.plkrhone.sisrh.util;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.plkrhone.sisrh.model.Endereco;
import com.plkrhone.sisrh.model.Estado;

public class EnderecoUtil {
    private static EnderecoUtil instance;
    public static EnderecoUtil getInstance(){
        if(instance==null) instance = new EnderecoUtil();
        return instance;
    }
    private EnderecoUtil(){}

    public Endereco pegarCEP(String cep){
        EnderecoUtil util = EnderecoUtil.getInstance();
        String url = "http://viacep.com.br/ws/"+ cep +"/json";
        JSONObject payload = util.getJSONFromUrl(url);
        if(payload!=null) return util.getEndereco(payload);
        else return null;
    }
    public void pegarPorEndereco(){
    }
    private JSONObject getJSONFromUrl(String urli) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urli);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf8"),8192);
            StringBuilder linhas = new StringBuilder(is.available());
            reader.lines().forEach(i -> linhas.append(i.trim()));
            JSONObject json = new JSONObject(linhas.toString());
            return json;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
    private Endereco getEndereco(JSONObject payload){
        Endereco endereco = new Endereco();
        endereco.setCep(payload.get("cep").toString());
        endereco.setLogradouro(payload.get("logradouro").toString());
        endereco.setComplemento(payload.get("complemento").toString());
        endereco.setBairro(payload.get("bairro").toString());
        endereco.setLocalidade(payload.get("localidade").toString());
        endereco.setUf(Estado.valueOf(payload.get("uf").toString()));
        endereco.setUnidade(payload.get("unidade").toString());
        endereco.setIbge(payload.get("ibge").toString());
        endereco.setGia(payload.get("gia").toString());
        return endereco;
    }
}
