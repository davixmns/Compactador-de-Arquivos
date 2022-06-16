import compactador.Compactador;
import compactador.Descompactador;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Compactador compactador = new Compactador();
        Descompactador descompactador = new Descompactador();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Apenas .txt", "txt");

        System.out.println("COMPACTADOR DE ARQUIVOS.TXT");
        System.out.println("Selecione um arquivo.txt UTF-8");

        chooser.setFileFilter(filter);

        int retorno = chooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            String caminhoEntrada = chooser.getSelectedFile().getAbsolutePath();
            BufferedReader arquivoEntrada = new BufferedReader(new FileReader(caminhoEntrada));
            long inicio = System.currentTimeMillis();
            compactador.compactar(arquivoEntrada);
            long fim = System.currentTimeMillis();
            System.out.println("Tempo da compactação -> " + (fim - inicio) + " milissegundos\n");
            java.awt.Desktop.getDesktop().open(new File("arquivos/saida/compactado.txt"));

            System.out.println("Deseja descompactar? 1-sim, 2-não");
            int escolha2 = scanner.nextInt();
            if (escolha2 == 1) {
                BufferedReader arquivoCompactado = new BufferedReader(new FileReader("arquivos/saida/compactado.txt"));
                long inicio2 = System.currentTimeMillis();
                descompactador.descompactar(arquivoCompactado);
                long fim2 = System.currentTimeMillis();
                System.out.println("Tempo da descompactação -> " + (fim2 - inicio2) + " milissegundos");
                java.awt.Desktop.getDesktop().open(new File("arquivos/auxiliar/tabela.txt"));
                java.awt.Desktop.getDesktop().open(new File("arquivos/saida/descompactado.txt"));
            }
        }

    }
}

