import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileReader("arquivos/arquivo.txt")); //scanner usando arquivo como entrada
        Frequencia frequencia = new Frequencia();
        FilaPrioridade fila = new FilaPrioridade();

        //LEITURA DO ARQUIVO E HISTOGRAMA
        while (scanner.hasNext()) { //Enquanto tem informações
            String linha = scanner.nextLine(); //linha do .txt
            for (int i = 0; i < linha.length(); i++) {
                int decimal = linha.charAt(i); //caractere -> decimal
                frequencia.incrementar(decimal); //índice do vetor++
            }
        }

        //EXIBIÇÃO DAS CONTAGENS
        for (int i = 0; i < frequencia.lenght(); i++) {
            int qtd = frequencia.getFrequencia(i);
            if (qtd > 0) { //frequência da letra > 0?
                char caractere = (char) i; //int -> caractere
                System.out.println(caractere + " = " + qtd); //exibe caractere e a frequência
                fila.enqueue(String.valueOf(caractere), qtd); //enfileira de acordo com a frequência
            }
        }
        fila.show();
        fila.dequeue();
        fila.show();


    }
}
