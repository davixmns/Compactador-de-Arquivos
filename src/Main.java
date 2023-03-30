import compactador.Compactador;
import compactador.Descompactador;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static Integer exibirMenu() {
        return JOptionPane.showOptionDialog(
                null,
                "Selecione um arquivo txt UTF-8",
                "Compactador de Arquivos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("arquivos/imagens/compac.png"),
                new String[]{"Compactar", "Descompactar", "Sair"},
                null
        );
    }

    public static Integer exibirCompactacaoConcluida(long inicio, long fim) {
        return JOptionPane.showOptionDialog(
                null,
                "Compactação concluída!\n" +
                        "Tempo de execução: " + (fim - inicio) + "ms",
                "Compactador de Arquivos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Ver arquivo", "Ver tabela", "Descompactar", "Menu", "Sair"},
                null
        );
    }

    public static Integer exibirDescompactacaoConcluida(long inicio, long fim) {
        return JOptionPane.showOptionDialog(
                null,
                "Descompactação concluída!\n" +
                        "Tempo de execução: " + (fim - inicio) + "ms",
                "Compactador de Arquivos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Ver arquivo", "Menu", "Sair"},
                null
        );
    }

    public static void exibirTabelaDeHuffman() throws IOException {
        java.awt.Desktop.getDesktop().open(new File("arquivos/saida/tabela.txt"));
    }

    public static void exibirArquivoDescompactado() throws IOException {
        java.awt.Desktop.getDesktop().open(new File("arquivos/saida/descompactado.txt"));
    }

    public static void exibirArquivoCompactado() throws IOException {
        java.awt.Desktop.getDesktop().open(new File("arquivos/saida/compactado.txt"));
    }

    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        Descompactador descompactador = new Descompactador();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Apenas .txt", "txt");
        chooser.setFileFilter(filter);

        int OPCAO_1 = 0;
        int OPCAO_2 = 1;
        int OPCAO_3 = 2;//
        int OPCAO_4 = 3;

        int escolhaDoMenu = exibirMenu();

        if (escolhaDoMenu == OPCAO_1) { //compactar
            int retorno = chooser.showOpenDialog(null);
            if (retorno == JFileChooser.APPROVE_OPTION) {
                String caminhoEntrada = chooser.getSelectedFile().getAbsolutePath();
                BufferedReader arquivoEntrada = new BufferedReader(new FileReader(caminhoEntrada));

                long inicioDaCompactacao = System.currentTimeMillis();
                compactador.compactar(arquivoEntrada);
                long fimDaCompactacao = System.currentTimeMillis();

                int escolhaDaCompactacaoConcluida = exibirCompactacaoConcluida(inicioDaCompactacao, fimDaCompactacao);

                boolean flagDescompactar = false;
                while (true) {
                    if (escolhaDaCompactacaoConcluida == OPCAO_1) {
                        exibirArquivoCompactado();
                        escolhaDaCompactacaoConcluida = exibirCompactacaoConcluida(inicioDaCompactacao, fimDaCompactacao);

                    } else if (escolhaDaCompactacaoConcluida == OPCAO_2) {
                        exibirTabelaDeHuffman();
                        escolhaDaCompactacaoConcluida = exibirCompactacaoConcluida(inicioDaCompactacao, fimDaCompactacao);

                    } else if (escolhaDaCompactacaoConcluida == OPCAO_3) {
                        flagDescompactar = true;
                        break;

                    } else if (escolhaDaCompactacaoConcluida == OPCAO_4) {
                        main(args);
                        break;

                    } else {
                        System.exit(0);
                    }
                }

                if (flagDescompactar) {
                    BufferedReader arquivoCompactado = new BufferedReader(new FileReader("arquivos/saida/compactado.txt"));

                    long inicioDaDescompactacao = System.currentTimeMillis();
                    descompactador.descompactar(arquivoCompactado);
                    long fimDaDescompactacao = System.currentTimeMillis();

                    int escolhaDaDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

                    while (true) {
                        if (escolhaDaDescompactacaoConcluida == OPCAO_1) {
                            exibirArquivoDescompactado();
                            escolhaDaDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

                        } else if (escolhaDaDescompactacaoConcluida == OPCAO_2) {
                            main(args);
                            break;

                        } else {
                            System.exit(0);
                        }
                    }
                }

            } else {
                main(args);
            }

        } else if (escolhaDoMenu == OPCAO_2) { //descompactar
            JFileChooser chooser2 = new JFileChooser();
            chooser2.setFileFilter(new FileNameExtensionFilter("Apenas .txt", "txt"));
            chooser2.showOpenDialog(null);
            String caminhoDoCompactado = chooser2.getSelectedFile().getAbsolutePath();
            BufferedReader arquivoCompactado = new BufferedReader(new FileReader(caminhoDoCompactado));

            long inicioDaDescompactacao = System.currentTimeMillis();
            descompactador.descompactar(arquivoCompactado);
            long fimDaDescompactacao = System.currentTimeMillis();

            int escolhaDeDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

            while (true) {
                if (escolhaDeDescompactacaoConcluida == OPCAO_1) {
                    exibirArquivoDescompactado();
                    escolhaDeDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

                } else if (escolhaDeDescompactacaoConcluida == OPCAO_2) {
                    main(args);
                    break;

                } else {
                    System.exit(0);
                }
            }

        }
    }
}
