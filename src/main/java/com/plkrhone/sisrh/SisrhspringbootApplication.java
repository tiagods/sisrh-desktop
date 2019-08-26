package com.plkrhone.sisrh;

import com.plkrhone.sisrh.util.AbstractJavaFxSupport;
import com.plkrhone.sisrh.util.Atualizador;
import com.plkrhone.sisrh.view.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SisrhspringbootApplication extends AbstractJavaFxSupport {

    private static Logger log = LoggerFactory.getLogger(SisrhspringbootApplication.class);

    @Autowired  static Atualizador atualizador;

    public static void main(String[] args) {
        launch(SisrhspringbootApplication.class, Login.class, args);
        /*
        if (atualizador.atualizacaoPendente()) {
            log.debug("Sistema desatualizado");
            atualizador.iniciarAtualizacao();
        } else {
            log.debug("Sistema atualizado");
            launch(SisrhspringbootApplication.class, Login.class, args);
        }
        ]*/

    }
}
