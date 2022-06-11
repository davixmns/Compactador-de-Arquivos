package davi;

import java.io.*;
import java.util.ArrayList;


public class Compactador {
    private final FilaPrioridade fila;
    private final Frequencia frequencia;
    private final PrintWriter escritorArquivoCompactado;
    private final ArrayList<Character> listaDeCaracteres;

    public Compactador() throws IOException {
        this.listaDeCaracteres = new ArrayList<>();
        this.fila = new FilaPrioridade();
        this.frequencia = new Frequencia();
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/arquivoCompactado.txt"));
    }

    private void contarFrequenciaDeCaracteres(BufferedReader arquivo) throws IOException {
        String linha = arquivo.readLine();
        while (linha != null) {
            System.out.println(linha);
            for (int i = 0; i < linha.length(); i++) {
                listaDeCaracteres.add(linha.charAt(i));
                int decimal = linha.charAt(i);
                this.frequencia.incrementar(decimal);
            }
            linha = arquivo.readLine();
        }
    }

    private void enfileirarCaracteres() {
        for (int i = 0; i < this.frequencia.lenght(); i++) {
            int qtd = this.frequencia.getFrequencia(i);
            if (qtd > 0) {
                this.fila.enqueue((char) i, qtd);
            }
        }
    }

    public void criarArvore() {
        while (this.fila.size() > 1) {
            No a = this.fila.dequeue();
            No b = this.fila.dequeue();
            this.fila.enqueue(a, b);
        }
        fila.printarArvore(this.fila.front());
    }

    private void escreverArvore(No raiz) {
        if (raiz.esquerdo != null) {
            this.escritorArquivoCompactado.print("0");
            escreverArvore(raiz.esquerdo);
        }

        if (raiz.direito != null) {
            this.escritorArquivoCompactado.print("1");
            escreverArvore(raiz.direito);
        }
    }

    private String getBinario(Character c) {
        return getBinario(c, this.fila.front(), new StringBuilder());
    }

    private String getBinario(Character c, No raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) {
            if (raiz.caractere == c) {
                return String.valueOf(s);
            }

        } else {
            //percorre a esquerda
            s.append('0');
            assert raiz.esquerdo != null;
            String esquerda = getBinario(c, raiz.esquerdo, s);
            s.deleteCharAt(s.length() - 1);

            // Percorre a direita
            s.append('1');
            String direita = getBinario(c, raiz.direito, s);
            s.deleteCharAt(s.length() - 1);

            if (esquerda == null)
                return direita;
            else
                return esquerda;
        }
        return null;
    }

    public void escreverMensagem(){
        for (Character caractere : listaDeCaracteres) {
            this.escritorArquivoCompactado.println(getBinario(caractere) + " " + caractere);
        }
    }

    public void compactar(BufferedReader arquivo) throws IOException {
        contarFrequenciaDeCaracteres(arquivo);
        enfileirarCaracteres();
        criarArvore();
        escreverArvore(this.fila.front());
        this.escritorArquivoCompactado.println();
        escreverMensagem();
        this.escritorArquivoCompactado.close();
    }

    public void descompactar(BufferedReader arquivo) throws IOException {

    }

    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        BufferedReader arquivo = new BufferedReader(new FileReader("arquivos/entrada/arquivo.txt"));

        compactador.compactar(arquivo);
    }
}
