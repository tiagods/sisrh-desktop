
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class RobotCatho {
    public static void main(String[] args){
//        Session session = HibernateFactory.getSession();
//        session.beginTransaction();
//        Criteria criteria = session.createCriteria(Cargo.class).add(Restrictions.isNull("descricao"));
//        List<Cargo> vagas = criteria.list();
//        try {
//            for (Cargo v : vagas) {
//                System.out.println(v.getNome());
//                String valor = Normalizer.normalize(v.getNome().trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
//                valor = valor.replace("+","-plus").replace("#","sharp").replace(".", "").replace(",","");
//                String url = "https://www.catho.com.br/profissoes/" + valor.toLowerCase().replace(" ", "-") + "/";
//                System.out.println(url);
//                JSONObject payload = getJSONFromUrl(url, null);
//                if (payload != null) {
//                    v.setDescricao(payload.get("description").toString().replace("&quot;", ""));
//                    v.setFonte(url);
//                }
//            }
//            for (int i = 0; i < vagas.size(); i++) {
//                System.out.println("Salvando objeto: " + i + " de " + vagas.size());
//                session.update(vagas.get(i));
//                if (i % 100 == 0) {
//                    session.flush();
//                    session.clear();
//                }
//            }
//            session.getTransaction().commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            session.close();
//        }
//        System.out.println("Concluido");

    }
    public static JSONObject getJSONFromUrl(String url) {
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
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
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
            json = new JSONObject(output.substring(output.indexOf(inicio)+inicio.length()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
