package upo.graph.impl;

import org.junit.jupiter.api.*;
import upo.graph.base.Graph;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestStructure {

    private static final String path = "upo.graph.impl.";
    private String matricola;
    private Graph grafo;

    @BeforeAll
    void setUp() {
        Class<?> datiClass = null;
        Class<?> graphClass = null;
        String classe = null;
        try {
            datiClass = Class.forName(path+"DatiStudente");
        } catch (ClassNotFoundException e) {
            Assertions.fail("Sembra che non esista la classe upo.graph.impl.DatiStudente. Creala come richiesto nel testo del compito.");
        }
        try {
            this.matricola = (String) datiClass.getField("matricola").get(null);
        } catch (Exception e) {
            Assertions.fail("Sembra che la classe DatiStudente non abbia il campo matricola, o che questo non sia public static String. Per favore, correggi.");
        }
        switch (matricola.charAt(matricola.length()-1)) {
            case '0':
            case '1':
                classe = "AdjListUndir";
                break;
            case '2':
            case '3':
            case '8':
                classe = "AdjListDir";
                break;
            case '4':
            case '5':
                classe = "AdjMatrixUndir";
                break;
            case '6':
            case '7':
            case '9':
                classe = "AdjMatrixDir";
                break;
        }
        try {
            graphClass = Class.forName(path+classe);
        } catch (ClassNotFoundException e) {
            Assertions.fail("Sembra che non esista la classe da implementare relativa alla tua matricola (dovrebbe essere "+path+classe+").");
        }
        try {
            grafo = (Graph) graphClass.newInstance();
        } catch (Exception e) {
            Assertions.fail("Sembra che la tua classe non abbia un costruttore senza parametri. Per favore implementalo.");
        }
    }

    @Test
    void test() {
        //doNothing
    }
}