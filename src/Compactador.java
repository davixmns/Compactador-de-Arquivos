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
        this.escritorArquivoTabela.print(gerarTabela());

        this.escritorArquivoCompactado.close();
        this.escritorArquivoTabela.close();

        System.out.println("Compactação concluída!");
    }

    private void contarFrequenciaDeCaracteres(BufferedReader arquivoEntrada) throws IOException {
        while (arquivoEntrada.ready()) {
            String linha = arquivoEntrada.readLine() + '~';
            for (int i = 0; i < linha.length(); i++) {
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
                fila.enqueue((char) i, qtd);
            }
        }
    }

    private void criarArvore() {
        while (fila.size() > 1) {
            NoFila a = fila.dequeue();
            NoFila b = fila.dequeue();
            fila.enqueue(a, b);
        }
    }

    private String getCaractereCodificado(Character c) {
        return getCaractereCodificado(c, fila.front(), new StringBuilder());
    }

    private String getCaractereCodificado(Character c, NoFila raiz, StringBuilder s) {
        if (raiz.esquerdo == null && raiz.direito == null) {
            if (raiz.caractere == c) {
                return String.valueOf(s);
            }

        } else {
            //percorre a esquerda
            s.append('0');
            assert raiz.esquerdo != null;
            String esquerda = getCaractereCodificado(c, raiz.esquerdo, s);
            s.deleteCharAt(s.length() - 1);

            // Percorre a direita
            s.append('1');
            String direita = getCaractereCodificado(c, raiz.direito, s);
            s.deleteCharAt(s.length() - 1);

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
            mensagemCodificada.append(getCaractereCodificado(caractere)).append(" ");
        }
        return mensagemCodificada.toString();
    }

    private String gerarTabela() {
        ArrayList<NoTabela> listaDeTabela = new ArrayList<>();
        StringBuilder tabela = new StringBuilder();

        for (int i = 0; i < this.frequencia.lenght(); i++) {
            if (this.frequencia.getFrequencia(i) > 0) {
                String codigo = getCaractereCodificado((char) i);
                String caractere = String.valueOf((char) i);

                listaDeTabela.add(new NoTabela(codigo, caractere));
            }
        }

        for (NoTabela no : listaDeTabela) {
            tabela.append(no.codigo).append("-").append(no.caractere).append("\n");
        }

        return tabela.toString();
    }


}
