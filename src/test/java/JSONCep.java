import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

public class JSONCep {
	static InputStream is = null;
    public static void main(String[] args){
        JSONParser jsonParser = new JSONParser();
        //String url = "https://www.catho.com.br/profissoes/abastecedor-de-maquinas/";
        String cep ="39401539";
        String url = "http://viacep.com.br/ws/"+ cep +"/json";
        JSONObject payload = jsonParser.getJSONFromUrl(url,null);
        payload.keySet().forEach(System.out::println);
    }
    public JSONObject getJSONFromUrl(String urli, List<String> params) {
        URL url = null;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urli);
            urlConnection = (HttpURLConnection)url.openConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String output = "";
        try {
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder(is.available());
            String line;
            
            reader.lines().forEach(i->total.append(i.trim()));
            
//            while ((line = reader.readLine()) != null) {
//                total.append(line).append('\n');
//            }
            output = total.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            urlConnection.disconnect();
        }
        JSONObject json = null;
        try {
            json = new JSONObject(output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
