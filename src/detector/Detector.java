package detector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brodock
 */
public class Detector {

    public Detector() {
        System.out.println("Bem vindo ao Detector de eventos aleatórios");
        try {
            MenuPrincipal();
        } catch (IOException ex) {
            Logger.getLogger(Detector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Falha de IO, por favor reinicie a aplicação");
        }
    }

    public void MenuPrincipal() throws IOException {

        while (true) {
            System.out.println("1- Definir novo evento");
            System.out.println("2- Ativar evento existente");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String comando = stdin.readLine();

            if (comando.startsWith("1")) {
                DefinirEvento();
            } else if (comando.startsWith("2")) {
                AtivarEvento();
            } else {
                msgOpcaoInvalida();
            }

        }


    }

    private void msgOpcaoInvalida() {
        System.out.println("Opção inválida, tente novamente!");
    }

    public static void main(String[] args) {
        Detector detector = new Detector();
        System.exit(0);
    }

    private void AtivarEvento() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void DefinirEvento() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
