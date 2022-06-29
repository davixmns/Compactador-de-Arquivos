package compactador;

import fila.Fila;
import fila.NoFila;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import frequencia.Frequencia;

public class Compactador {
    private static Fila fila;
    private final Frequencia frequencia;
    private final PrintWriter escritorArquivoCompactado;
    private final ArrayList<Character> listaDeCaracteres;
    private final PrintWriter escritorArquivoTabela;
    private String arvoreCodificada = "";

    public Compactador() throws IOException {
        this.listaDeCaracteres = new ArrayList<>();
        fila = new Fila();
        this.frequencia = new Frequencia();
        this.escritorArquivoTabela = new PrintWriter(new FileWriter("arquivos/saida/tabela.txt"));
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/compactado.txt"));
    }

    public void compactar(BufferedReader arquivoEntrada) throws IOException {
        contarFrequenciaDeCaracteres(arquivoEntrada);
        enfileirarCaracteres();
        criarArvore();
        escreverArvoreEmPreOrdem();
        escreverMensagemCodificada();
        gerarTabelaDeHuffman();

        this.escritorArquivoCompactado.close(); //fecha arquivo
        this.escritorArquivoTabela.close(); //fecha arquivo
    }

    private void contarFrequenciaDeCaracteres(BufferedReader arquivoEntrada) throws IOException {
        System.out.println("Contando caracteres...");
        while (arquivoEntrada.ready()) {
            String linha = arquivoEntrada.readLine() + '\n'; //usa ~ para quebrar a linha futuramente
            for (int i = 0; i < linha.length(); i++) {
                listaDeCaracteres.add(linha.charAt(i)); //adiciona o caractere na lista para usar futuramente
                this.frequencia.incrementarNoIndiceDoVetor(linha.charAt(i)); //incrementa no local especificado no vetor
            }
        }
    }

    private void enfileirarCaracteres() {
        System.out.println("Enfileirando caracteres...");
        for (int i = 0; i < this.frequencia.lenght(); i++) {
            int frequenciaDoCaractere = this.frequencia.getFrequencia(i);
            if (frequenciaDoCaractere > 0) { //adiciona todos os [] > 0 na fila de prioridade
                fila.enqueue((char) i, frequenciaDoCaractere); //guarda um novo No(caractere, frequência) na fila
            }
        }
    }

    private void criarArvore() { //Cria Arvore de Huffman
        System.out.println("Construindo árvore...");
        while (fila.size() > 1) {
            NoFila a = fila.dequeue();
            NoFila b = fila.dequeue();
            NoFila pai = new NoFila(a, b); //novo No pai(a, b)
            fila.enqueue(pai); //enfileira o pai
        }
    }

    private void escreverArvoreEmPreOrdem() {
        System.out.println("Escrevendo árvore...");
        escreverArvoreEmPreOrdem(fila.front());
        this.escritorArquivoCompactado.print(this.arvoreCodificada);
    }

    private void escreverArvoreEmPreOrdem(NoFila raiz) { //preOrdem
        if (raiz.esquerdo == null && raiz.direito == null) { //folha
            this.arvoreCodificada += "1";
            this.arvoreCodificada += String.format("%8s", Integer.toBinaryString(raiz.caractere)).replaceAll(" ", "0");
            //1 + 8 bits do caractere
            return;
        }
        this.arvoreCodificada += "0";
        if (raiz.esquerdo != null) {
            escreverArvoreEmPreOrdem(raiz.esquerdo);
        }
        if (raiz.direito != null) {
            escreverArvoreEmPreOrdem(raiz.direito);
        }
    }

    private String getCaractereCodificado(Character c) { //exemplo: 'a' -> '0111'
        return getCaractereCodificado(c, fila.front(), new StringBuilder());
    }

    private String getCaractereCodificado(Character c, NoFila raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) { //folha
            if (raiz.caractere.equals(c)) { //se o caractere for o mesmo que o caractere da folha
                return String.valueOf(s); //retorna o código
            }

        } else {
            //percorre a esquerda
            s.append('0');
            assert raiz.esquerdo != null;
            String esquerda = getCaractereCodificado(c, raiz.esquerdo, s); //recursividade
            s.deleteCharAt(s.length() - 1); //remove o ultimo caractere

            // Percorre a direita
            s.append('1');
            String direita = getCaractereCodificado(c, raiz.direito, s); //recursividade
            s.deleteCharAt(s.length() - 1); //remove ultimo caractere

            if (esquerda == null)
                return direita;
            else
                return esquerda;
        }
        return null;
    }

    private void escreverMensagemCodificada() {
        System.out.println("Escrevendo mensagem codificada...");
        StringBuilder mensagemCodificada = new StringBuilder();
        this.escritorArquivoCompactado.println();
        for (Character caractere : this.listaDeCaracteres) {
            mensagemCodificada.append(getCaractereCodificado(caractere)); //escreve o código no arquivo
        }
        this.escritorArquivoCompactado.print(mensagemCodificada);
        System.out.println("Compactação concluída!");
    }

    private void gerarTabelaDeHuffman() { //Tabela de huffman
        this.escritorArquivoTabela.println("TABELA DE HUFFMAN");
        for (int i = 0; i < this.frequencia.lenght(); i++) { //percorre o vetor com as frequências
            if (this.frequencia.getFrequencia(i) > 0) {  //se a frequencia for maior que 0
                this.escritorArquivoTabela.println((char) i + " -> " + getCaractereCodificado((char) i));
            }
        }
    }
}
