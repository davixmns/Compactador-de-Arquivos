import compactador.Compactador;
import compactador.Descompactador;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static Integer ExibirArquivoInvalido() {
        return JOptionPane.showOptionDialog(
                null,
                "Arquivo inválido!",
                "Compactador de Arquivos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[]{"Tentar Novamente", "Sair"},
                "OK"
        );
    }

    public static Integer exibirMenu() {
        return JOptionPane.showOptionDialog(
                null,
                "Selecione um arquivo txt UTF-8",
                "Compactador de Arquivos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
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

        int escolhaDoMenu = exibirMenu();

        int compactar = 0;
        int descompactar = 1;
        int opcao1 = 0;
        int opcao2 = 1;
        int opcao3 = 2;
        int opcao4 = 3;
        int opcao5 = 4;

        if(escolhaDoMenu == compactar){
            int retorno = chooser.showOpenDialog(null);
            if(retorno == JFileChooser.APPROVE_OPTION) {
                String caminhoEntrada = chooser.getSelectedFile().getAbsolutePath();
                BufferedReader arquivoEntrada = new BufferedReader(new FileReader(caminhoEntrada));

                long inicioDaCompactacao = System.currentTimeMillis();
                compactador.compactar(arquivoEntrada);
                long fimDaCompactacao = System.currentTimeMillis();

                int escolhaDaCompactacaoConcluida = exibirCompactacaoConcluida(inicioDaCompactacao, fimDaCompactacao);

                while (true) {
                    if (escolhaDaCompactacaoConcluida == opcao1) {
                        exibirArquivoCompactado();

                    } else if (escolhaDaCompactacaoConcluida == opcao2) {
                        exibirTabelaDeHuffman();

                    } else if (escolhaDaCompactacaoConcluida == opcao3) {
                        BufferedReader arquivoCompactado = new BufferedReader(new FileReader("arquivos/saida/compactado.txt"));
                        long inicioDaDescompactacao = System.currentTimeMillis();
                        descompactador.descompactar(arquivoCompactado);
                        long fimDaDescompactacao = System.currentTimeMillis();

                        int escolhaDaDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

                        while (true) {
                            if (escolhaDaDescompactacaoConcluida == opcao1) {
                                exibirArquivoDescompactado();

                            } else if (escolhaDaDescompactacaoConcluida == opcao2) {
                                main(args);
                                break;

                            } else if(escolhaDaDescompactacaoConcluida == opcao3){
                                System.exit(0);
                                break;
                            }

                            escolhaDaDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);
                        }

                    } else if (escolhaDaCompactacaoConcluida == opcao4) {
                        main(args);
                        break;

                    } else if(escolhaDaCompactacaoConcluida == opcao5){
                        System.exit(0);
                        break;
                    }

                    escolhaDaCompactacaoConcluida = exibirCompactacaoConcluida(inicioDaCompactacao, fimDaCompactacao);
                }

            } else {
                main(args);
            }

        } else if (escolhaDoMenu == descompactar) {
            JFileChooser chooser2 = new JFileChooser();
            FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Apenas .txt", "txt");

            boolean chave = true;
            boolean chave2 = true;
            do {
                chooser2.setFileFilter(filter2);
                int retorno = chooser2.showOpenDialog(null);

                if (retorno == JFileChooser.APPROVE_OPTION) {
                    String caminhoEntrada = chooser2.getSelectedFile().getAbsolutePath();
                    BufferedReader arquivoCompactado = new BufferedReader(new FileReader(caminhoEntrada));

                    String linha = arquivoCompactado.readLine();

                    for (int i = 0; i < linha.length(); i++) {
                        if (linha.charAt(i) != '0' && linha.charAt(i) != '1') {
                            int escolhaDeArquivoInvalido = ExibirArquivoInvalido();
                            if (escolhaDeArquivoInvalido == 1) {
                                System.exit(0);
                            }
                            chave2 = false;
                            break;
                        }
                    }
                    chave = !chave2;
                } else {
                    chave = false;
                    main(args);
                }
            } while (chave);

            long inicioDaDescompactacao = System.currentTimeMillis();
            descompactador.descompactar(new BufferedReader(new FileReader(chooser2.getSelectedFile().getAbsolutePath())));
            long fimDaDescompactacao = System.currentTimeMillis();

            int escolhaDeDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

            while(true){
                if(escolhaDeDescompactacaoConcluida == opcao1){
                    exibirArquivoDescompactado();
                    escolhaDeDescompactacaoConcluida = exibirDescompactacaoConcluida(inicioDaDescompactacao, fimDaDescompactacao);

                } else if(escolhaDeDescompactacaoConcluida == opcao2){
                    main(args);
                    break;

                } else if(escolhaDeDescompactacaoConcluida == opcao3) {
                    System.exit(0);
                    break;
                }
            }

        }
    }
}
