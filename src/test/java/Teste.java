
import com.plkrhone.sisrh.controller.PersistenciaController;

public class Teste extends PersistenciaController {
    public static void main(String[] args) {
        new Teste();
    }

    private Teste() {
        loadFactory();

        close();
    }
}


