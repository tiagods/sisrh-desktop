import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Prolink on 20/07/2017.
 */
public class LoadPage {

    public void getPage(URL url, File file) throws IOException {
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {

            // Imprime p&aacute;gina no console
            System.out.println(inputLine);

            // Grava pagina no arquivo
            out.write(inputLine);
            out.newLine();
        }

        in.close();
        out.flush();
        out.close();
    }

    public static void main(String[] args) {
        URL url = null;
        File file = new File("C:\\temp\\page.html");
        try {
            url = new URL("https://www.catho.com.br/profissoes/assistente-de-service-desk");
            new LoadPage().getPage(url, file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
