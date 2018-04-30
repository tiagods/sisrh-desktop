package com.plkrhone.sisrh.util;


import org.json.JSONObject;

import com.plkrhone.sisrh.model.Endereco;
import com.plkrhone.sisrh.model.Estado;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

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

    public static void main(String[] args){
        EnderecoUtil util = EnderecoUtil.getInstance();
        //String url = "https://www.catho.com.br/profissoes/abastecedor-de-maquinas/";
        String cep ="05765200";
        String url = "http://viacep.com.br/ws/"+ cep +"/json";
        JSONObject payload = util.getJSONFromUrl(url);
        payload.keySet().forEach(System.out::println);
        if(payload!=null){
            Endereco e = util.getEndereco(payload);
            System.out.println(e.getLogradouro());
        }
        else
            System.out.println("Verifique sua conexão com a internet ou");
    }
    private JSONObject getJSONFromUrl(String urli) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = url = new URL(urli);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
