import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prolink on 21/07/2017.
 */
public class JSONParser {
	static InputStream is = null;
    public static void main(String[] args) throws JSONException {
        JSONParser jsonParser = new JSONParser();
        //String url = "https://www.catho.com.br/profissoes/abastecedor-de-maquinas/";
        String url = "https://www.catho.com.br/profissoes/supervisor-industrial/";
        JSONObject payload = jsonParser.getJSONFromUrl(
                url);
        System.out.println(payload.get("Name"));
        System.out.println(payload.get("description").toString().replace("&quot;",""));
        System.out.println(payload.get("baseSalary"));
    }

    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url) {
        URL _url;
        HttpURLConnection urlConnection;

        try {
            _url = new URL(url);
            urlConnection = (HttpURLConnection) _url.openConnection();
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
            while ((line = reader.readLine()) != null) {
                total.append(line).append('\n');
            }
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
            String inicio="<script type=\"application/ld+json\">\n";
            System.out.println(output.substring(output.indexOf(inicio)+inicio.length()));
            json = new JSONObject(output.substring(output.indexOf(inicio)+inicio.length()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
