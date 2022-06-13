import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        Descompactador descompactadorr = new Descompactador();

        System.out.println("COMPACTADOR DE ARQUIVOS.TXT");

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Selecione apenas arquivos .txt",
                "txt"
        );
        chooser.setFileFilter(filter);

        int retorno = chooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            String caminhoEntrada = chooser.getSelectedFile().getAbsolutePath();

            BufferedReader arquivoEntrada = new BufferedReader(new FileReader(caminhoEntrada));
            BufferedReader arquivoCompactado = new BufferedReader(new FileReader("arquivos/saida/compactado.txt"));

            compactador.compactar(arquivoEntrada);
            descompactadorr.descompactar(arquivoCompactado);
            java.awt.Desktop.getDesktop().open(new File("arquivos/auxiliar/tabela.txt"));
            java.awt.Desktop.getDesktop().open(new File("arquivos/saida/descompactado.txt"));
            java.awt.Desktop.getDesktop().open(new File("arquivos/saida/compactado.txt"));

        }
    }
}
