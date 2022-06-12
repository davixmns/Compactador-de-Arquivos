import java.io.*;
import java.util.ArrayList;

public class Descompactador {
    private final PrintWriter escritorArquivoDescompactado;
    private final BufferedReader leitorArquivoTabela;

    public Descompactador() throws IOException {
        this.escritorArquivoDescompactado = new PrintWriter(new FileWriter("arquivos/saida/descompactado.txt"));
        this.leitorArquivoTabela = new BufferedReader(new FileReader("arquivos/auxiliar/tabela.txt"));
    }

    //lê o arquivo para decodificar
    private String decodificarTabela(BufferedReader arquivo) throws IOException {
        ArrayList<NoTabela> listaNoTabela = new ArrayList<>();
        StringBuilder mensagemDescompactada = new StringBuilder();

        String linha = this.leitorArquivoTabela.readLine();
        while (linha != null) {
            String[] partes = linha.split("-");
            if (partes[1].equals("~")) {
                partes[1] = "\n";
            }
            listaNoTabela.add(new NoTabela(partes[0], partes[1]));
            linha = this.leitorArquivoTabela.readLine();
        }

        String[] mensagemCodificada = arquivo.readLine().split(" ");

        for (String codigo : mensagemCodificada) {
            for (NoTabela no : listaNoTabela) {
                if (codigo.equals(no.codigo)) {
                    mensagemDescompactada.append(no.caractere);
                }
            }
        }

        return mensagemDescompactada.toString();
    }

    //percorre a arvore para decodificar
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
                if(raiz.caractere == '~') {
                    decodificado.append('\n');
                } else {
                    decodificado.append(raiz.caractere);
                }
                raiz = Compactador.fila.front();
            }
        }

        return decodificado.toString();
    }

    public void descompactar(BufferedReader arquivoCompactado) throws IOException {
//        this.escritorArquivoDescompactado.print(decodificarTabela(arquivoCompactado));
        this.escritorArquivoDescompactado.print(decodificarMensagem(Compactador.fila.front(), arquivoCompactado.readLine()));

        this.escritorArquivoDescompactado.close();
        System.out.println("Descompactação concluída!");
    }
}
