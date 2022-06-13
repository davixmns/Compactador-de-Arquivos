import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Compactador {
    public static Fila fila;
    public final Frequencia frequencia;
    public final PrintWriter escritorArquivoCompactado;
    public final ArrayList<Character> listaDeCaracteres;
    public final PrintWriter escritorArquivoTabela;
    public String arvoreCodificada = "";

    public Compactador() throws IOException {
        this.listaDeCaracteres = new ArrayList<>();
        fila = new Fila();
        this.frequencia = new Frequencia();
        this.escritorArquivoTabela = new PrintWriter(new FileWriter("arquivos/auxiliar/tabela.txt"));
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/compactado.txt"));
    }

    public void compactar(BufferedReader arquivoEntrada) throws IOException {
        contarFrequenciaDeCaracteres(arquivoEntrada);
        enfileirarCaracteres();
        criarArvore();
        printarArvore();
        escreverMensagem();
        gerarTabela();

        this.escritorArquivoCompactado.close(); //fecha arquivo
        this.escritorArquivoTabela.close(); //fecha arquivo

        System.out.println("Compactação concluída!");
    }

    private void contarFrequenciaDeCaracteres(BufferedReader arquivoEntrada) throws IOException {
        while (arquivoEntrada.ready()) {
            String linha = arquivoEntrada.readLine() + '~'; //usa ~ para quebrar a linha futuramente
            for (int i = 0; i < linha.length(); i++) {
                listaDeCaracteres.add(linha.charAt(i)); //adiciona o caractere na lista para usar futuramente
                this.frequencia.incrementar(linha.charAt(i)); //incrementa no local especificado no vetor
            }
        }
    }

    private void enfileirarCaracteres() {
        for (int i = 0; i < this.frequencia.lenght(); i++) {
            int qtd = this.frequencia.getFrequencia(i);
            if (qtd > 0) { //adiciona todos os [] > 0 na fila de prioridade
                fila.enqueue((char) i, qtd); //guarda um novo No(caractere, frequência) na fila
            }
        }
    }

    private void criarArvore() { //Cria Arvore de Huffman
        while (fila.size() > 1) {
            NoFila a = fila.dequeue();
            NoFila b = fila.dequeue();
            fila.enqueue(a, b); //cria novo No(frequência de a, frequência de b) e adiciona na fila
        }
    }

    public void printarArvore(){
        printarArvore(fila.front());
        this.escritorArquivoCompactado.print(this.arvoreCodificada);
    }

    public void printarArvore(NoFila raiz) {
        if (raiz.caractere != null) {
            this.arvoreCodificada += "1";
            this.arvoreCodificada += String.format("%8s", Integer.toBinaryString(raiz.caractere)).replaceAll(" ", "0");
            return;
        }
        this.arvoreCodificada += "0";
        if (raiz.esquerdo != null) {
            printarArvore(raiz.esquerdo);
        }
        if (raiz.direito != null) {
            printarArvore(raiz.direito);
        }
    }

    private String getCaractereCodificado(Character c) { //exemplo: 'a' -> '0111'
        return getCaractereCodificado(c, fila.front(), new StringBuilder());
    }

    private String getCaractereCodificado(Character c, NoFila raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) { //folha
            if (raiz.caractere == c) { //se o caractere for o mesmo que o caractere da folha
                return String.valueOf(s);
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

    private void escreverMensagem() {
        this.escritorArquivoCompactado.println();
        for (Character caractere : this.listaDeCaracteres) {
            this.escritorArquivoCompactado.print(getCaractereCodificado(caractere)); //adiciona o caractere codificado no StringBuilder
        }
    }

    private void gerarTabela() { //Tabela de huffman
        this.escritorArquivoTabela.println("TABELA DE HUFFMAN");
        for (int i = 0; i < this.frequencia.lenght(); i++) { //percorre o vetor com as frequências
            if (this.frequencia.getFrequencia(i) > 0) {  //se a frequencia for maior que 0
                this.escritorArquivoTabela.println((char)i + " -> " + getCaractereCodificado((char)i));
            }
        }
        System.out.println("Tabela gerada!");
    }
}
