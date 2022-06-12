import java.io.*;
import java.util.ArrayList;

public class Compactador {
    public static FilaPrioridade fila;
    private final Frequencia frequencia;
    private final PrintWriter escritorArquivoCompactado;
    private final ArrayList<Character> listaDeCaracteres;
    private final PrintWriter escritorArquivoTabela;

    public Compactador() throws IOException {
        this.listaDeCaracteres = new ArrayList<>();
        fila = new FilaPrioridade();
        this.frequencia = new Frequencia();
        this.escritorArquivoTabela = new PrintWriter(new FileWriter("arquivos/auxiliar/tabela.txt"));
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/compactado.txt"));
    }

    public void compactar(BufferedReader arquivoEntrada) throws IOException {
        contarFrequenciaDeCaracteres(arquivoEntrada);
        enfileirarCaracteres();
        criarArvore();
        this.escritorArquivoCompactado.print(escreverMensagem());
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

    private String escreverMensagem() {
        StringBuilder mensagemCodificada = new StringBuilder();
        for (Character caractere : this.listaDeCaracteres) {
            mensagemCodificada.append(getCaractereCodificado(caractere)).append(" "); //adiciona o caractere codificado no StringBuilder
        }
        return mensagemCodificada.toString(); //retorna mensagem codificada
    }

    private void gerarTabela() { //Tabela de huffman
        StringBuilder tabela = new StringBuilder(); //StringBuilder para armazenar a tabela
        for (int i = 0; i < this.frequencia.lenght(); i++) { //percorre o vetor com as frequências
            if (this.frequencia.getFrequencia(i) > 0) {  //se a frequencia for maior que 0
                this.escritorArquivoTabela.println(getCaractereCodificado((char)i) + "|" + (char)i); //adiciona codigo e caractere no StringBuilder
            }
        }
    }
}
