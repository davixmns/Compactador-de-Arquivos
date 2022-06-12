import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Compactador compactador = new Compactador();
        Descompactador descompactador = new Descompactador();

        BufferedReader arquivoEntrada = new BufferedReader(new FileReader("arquivos/entrada/arquivo.txt"));
        BufferedReader arquivoCompactado = new BufferedReader(new FileReader("arquivos/saida/compactado.txt"));

        compactador.compactar(arquivoEntrada);
        descompactador.descompactar(arquivoCompactado);

    }
}
