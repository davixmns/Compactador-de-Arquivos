package davi;

import java.io.*;
import java.util.ArrayList;


public class Compactador {
    private final FilaPrioridade fila;
    private final Frequencia frequencia;
    private final PrintWriter escritorArquivoCompactado;
    private final ArrayList<Character> listaDeLetras;

    public Compactador() throws IOException {
        this.listaDeLetras = new ArrayList<>();
        this.fila = new FilaPrioridade();
        this.frequencia = new Frequencia();
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/arquivoCompactado.txt"));
    }

    public void contarFrequenciaDeCaracteres(BufferedReader arquivo) throws IOException {
        String linha = arquivo.readLine();
        while (linha != null) {
            System.out.println(linha);
            for (int i = 0; i < linha.length(); i++) {
                listaDeLetras.add(linha.charAt(i));
                int decimal = linha.charAt(i);
                this.frequencia.incrementar(decimal);
            }
            linha = arquivo.readLine();
        }
    }

    public void enfileirarCaracteres() {
        for (int i = 0; i < this.frequencia.lenght(); i++) {
            int qtd = this.frequencia.getFrequencia(i);
            if (qtd > 0) {
                this.fila.enqueue((char) i, qtd);
            }
        }
        fila.show();
    }

//    private String[] mensagemBinaria(No root) {
//        String[] vetor = new String[256];
//        mensagemBinaria(vetor, root, "");
//        return vetor;
//    }
//
//    private void mensagemBinaria(String[] vetor, No raiz, String string) {
//        System.out.print(raiz.caractere + "-");
//        if (raiz.esquerdo == null && raiz.direito == null) {
//            vetor[raiz.caractere] = string;
//            return;
//        }
//        assert raiz.esquerdo != null;
//        mensagemBinaria(vetor, raiz.esquerdo, string + '0');
//        mensagemBinaria(vetor, raiz.direito, string + '1');
//    }

    public void escreverArvore(No raiz) {
        if (raiz.esquerdo != null) {
            this.escritorArquivoCompactado.print("0");
            escreverArvore(raiz.esquerdo);
        }

        if (raiz.direito != null) {
            this.escritorArquivoCompactado.print("1");
            escreverArvore(raiz.direito);
        }
    }

    public String mensagemBinaria(Character c) {
        return mensagemBinaria(c, this.fila.front(), new StringBuilder());
    }

    public String mensagemBinaria(Character c, No raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) {
            if (raiz.caractere == c) {
                return String.valueOf(s);
            }

        } else {
            s.append('0');
            assert raiz.esquerdo != null;
            String esquerda = mensagemBinaria(c, raiz.esquerdo, s);
            s.deleteCharAt(s.length() - 1);

            // Percorre a direita
            s.append('1');
            String direita = mensagemBinaria(c, raiz.direito, s);
            s.deleteCharAt(s.length() - 1);

            if (esquerda == null)
                return direita;
            else
                return esquerda;
        }
        return null;
    }

    public void compactar(BufferedReader arquivo) throws IOException {
        contarFrequenciaDeCaracteres(arquivo);
        enfileirarCaracteres();

        while (this.fila.size() != 1) {
            No a = this.fila.dequeue();
            No b = this.fila.dequeue();

            this.fila.enqueue(a, b);
            fila.show();
        }

        escreverArvore(this.fila.front());
        this.escritorArquivoCompactado.println();

        for (Character listaDeLetra : listaDeLetras) {
            escritorArquivoCompactado.print(mensagemBinaria(listaDeLetra));
        }

        this.escritorArquivoCompactado.close();
    }


    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        BufferedReader arquivo = new BufferedReader(new FileReader("arquivos/entrada/arquivo.txt"));

        compactador.compactar(arquivo);
    }
}
