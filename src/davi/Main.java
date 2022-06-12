package davi;

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


//        System.out.println("1 - Compactar, 2 - Descompactar, 3 - Sair");
//        int opcao = scanner.nextInt();
//
//        while (opcao != 3) {
//            switch (opcao) {
//                case 1 -> compactador.compactar(arquivoEntrada);
//                case 2 -> descompactador.decodificarTabela(arquivoCompactado);
//            }
//            opcao = scanner.nextInt();
//        }
    }
}
