package davi;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Compactador {
    private final FilaPrioridade fila;
    private final Frequencia frequencia;
    private PrintWriter escritorArquivoCompactado;

    public Compactador() throws IOException {
        this.fila = new FilaPrioridade();
        this.frequencia = new Frequencia();
        this.escritorArquivoCompactado = new PrintWriter(new FileWriter("arquivos/saida/arquivoCompactado.txt"));
    }

    public void compactar(Scanner arquivo) {

        while (arquivo.hasNextLine()) {
            String linha = arquivo.nextLine();
            for(int i = 0; i < linha.length(); i++) {
                int decimal = linha.charAt(i);
                frequencia.incrementar(decimal);
            }
        }

        for (int i = 0; i < frequencia.lenght(); i++) {
            int qtd = frequencia.getFrequencia(i);
            if (qtd > 0) {
                char caractere = (char) i;
                this.fila.enqueue(caractere , qtd);
            }
        }

        while(fila.size() != 1) {
            No a = fila.dequeue();
            No b = fila.dequeue();

            this.fila.enqueue(a, b);
            this.fila.show();
        }

        System.out.println(fila.size());

        escreverCompactado(this.fila.front(), this.escritorArquivoCompactado);
        escritorArquivoCompactado.close();
    }

    public void escreverCompactado(No raiz, PrintWriter escritorCriptografado){
        if(raiz.esquerdo != null){
            escritorCriptografado.print(0);
            escreverCompactado(raiz.esquerdo, escritorCriptografado);
        }
        if(raiz.direito != null){
            escritorCriptografado.print(1);
            escreverCompactado(raiz.direito, escritorCriptografado);
        }
    }

    public static void main(String[] args) throws IOException {
        Compactador compactador = new Compactador();
        Scanner arquivo = new Scanner(new FileReader("arquivos/entrada/arquivo.txt"));

        compactador.compactar(arquivo);
    }
}
