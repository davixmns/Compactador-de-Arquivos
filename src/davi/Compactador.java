package davi;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Compactador {
    private final FilaPrioridade fila;
    private final Frequencia frequencia;
    private final PrintWriter escritorArquivoCompactado;
    private final PrintWriter escritorArquivoDescompactado;
    private final ArrayList<Character> listaDeCaracteres;
    private final PrintWriter escritorArquivoTabela;
    private final Scanner leitorArquivoTabela;

    public Compactador() throws IOException {
        this.listaDeCaracteres = new ArrayList<>();
        this.fila = new FilaPrioridade();
        this.frequencia = new Frequencia();
        this.leitorArquivoTabela = new Scanner(new File("arquivos/auxiliar/tabela.txt"));
        this.escritorArquivoTabela = new PrintWriter(new FileWriter("arquivos/auxiliar/tabela.txt"));
        this.escritorArquivoDescompactado = new PrintWriter(new FileWriter("arquivos/saida/descompactado.txt"));
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/compactado.txt"));
    }

    private void contarFrequenciaDeCaracteres(Scanner arquivo) {
        while (arquivo.hasNext()) {
            String linha = arquivo.nextLine() + '\n';
            for (int i = 0; i < linha.length(); i++) {
                System.out.print(linha.charAt(i));
                listaDeCaracteres.add(linha.charAt(i));
                int decimal = linha.charAt(i);
                this.frequencia.incrementar(decimal);
            }
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
            NoFila a = this.fila.dequeue();
            NoFila b = this.fila.dequeue();
            this.fila.enqueue(a, b);
        }
    }

    private String getLetraCodificada(Character c) {
        return getLetraCodificada(c, this.fila.front(), new StringBuilder());
    }

    private String getLetraCodificada(Character c, NoFila raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) {
            if (raiz.caractere == c) {
                return String.valueOf(s);
            }

        } else {
            //percorre a esquerda
            s.append('0');
            assert raiz.esquerdo != null;
            String esquerda = getLetraCodificada(c, raiz.esquerdo, s);
            s.deleteCharAt(s.length() - 1);

            // Percorre a direita
            s.append('1');
            String direita = getLetraCodificada(c, raiz.direito, s);
            s.deleteCharAt(s.length() - 1);

            if (esquerda == null)
                return direita;
            else
                return esquerda;
        }
        return null;
    }

    public void escreverMensagem() {
        for (Character caractere : listaDeCaracteres) {
            String letraCodificada = getLetraCodificada(caractere);
            this.escritorArquivoCompactado.print(letraCodificada + " ");
        }
        this.escritorArquivoCompactado.close();
    }

    public void compactar(Scanner arquivo) {
        contarFrequenciaDeCaracteres(arquivo);
        enfileirarCaracteres();
        criarArvore();
        escreverMensagem();
    }


    public String decodificarMensagem(NoFila raiz, String mensagemCodificada) {
        StringBuilder decodificado = new StringBuilder();

        for (int i = 0; i < mensagemCodificada.length(); i++) {
            if (mensagemCodificada.charAt(i) == '0') {
                raiz = raiz.esquerdo;
            } else if (mensagemCodificada.charAt(i) == '1') {
                raiz = raiz.direito;
            }

            assert raiz != null;
            if (raiz.esquerdo == null && raiz.direito == null) {
                decodificado.append(raiz.caractere);
                raiz = this.fila.front();
            }
        }

        return decodificado.toString();
    }

    public void gerarTabela() {
        for (int i = 0; i < this.frequencia.lenght(); i++) {
            if (this.frequencia.getFrequencia(i) > 0) {
                if ((char) i == '\n') {
                    this.escritorArquivoTabela.print(getLetraCodificada((char) i) + " \\n");
                } else {
                    this.escritorArquivoTabela.println(getLetraCodificada((char) i) + "-" + (char) i);
                }
            }
        }
        this.escritorArquivoTabela.close();
    }

    public void decodificarTabela(Scanner arquivo) {
        ArrayList<NoTabela> listaNoTabela = new ArrayList<>();

        while (this.leitorArquivoTabela.hasNextLine()) {
            String[] linha = this.leitorArquivoTabela.nextLine().split("-");
            listaNoTabela.add(new NoTabela(linha[0], linha[1]));
        }

        String[] mensagemCodificada = arquivo.nextLine().split(" ");

        for (String codigo : mensagemCodificada) {
            for (NoTabela no : listaNoTabela) {
                if (codigo.equals(no.codigo)) {
                    this.escritorArquivoDescompactado.print(no.caractere);
                }
            }
        }

        this.escritorArquivoDescompactado.close();
    }

    public void descompactar(Scanner arquivo) {
        gerarTabela();
        decodificarTabela(arquivo);
    }

    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        Scanner arquivoEntrada = new Scanner(new FileReader("arquivos/entrada/arquivo.txt"));
        Scanner arquivoCompactado = new Scanner(new FileReader("arquivos/saida/compactado.txt"));
        compactador.compactar(arquivoEntrada);
        compactador.descompactar(arquivoCompactado);
    }
}
