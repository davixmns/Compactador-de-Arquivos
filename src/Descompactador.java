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
    private String decodificarTabela(BufferedReader arquivoCompactado) throws IOException {
        ArrayList<NoTabela> listaNoTabela = new ArrayList<>(); //Lista de NoTabela(código, caractere)
        StringBuilder mensagemDecodificada = new StringBuilder(); //StringBuilder para mensagem decodificada

        String linha = this.leitorArquivoTabela.readLine();
        while (linha != null) {
            String[] partes = linha.split("\\|"); //separa linha em código e caractere
            if (partes[1].equals("~")) { //se o caractere for "~", substitui por "\n"
                partes[1] = "\n";
            }
            listaNoTabela.add(new NoTabela(partes[0], partes[1])); //adiciona na lista de NoTabela
            linha = this.leitorArquivoTabela.readLine();
        }

        String[] mensagemCodificada = arquivoCompactado.readLine().split(" "); //separa códigos no vetor
        for (String codigo : mensagemCodificada) { //percorre o vetor de códigos para consultar na tabela de códigos
            for (NoTabela no : listaNoTabela) {
                if (codigo.equals(no.codigo)) {
                    mensagemDecodificada.append(no.caractere); //adiciona caractere na StringBuilder
                }
            }
        }

        return mensagemDecodificada.toString(); //retorna mensagem decodificada
    }

    //percorre a arvore para decodificar
    public String decodificarMensagem(NoFila raiz, String mensagemCodificada) {
        StringBuilder mensagemDecodificada = new StringBuilder(); //StringBuilder para mensagem decodificada

        for (int i = 0; i < mensagemCodificada.length(); i++) {
            if (mensagemCodificada.charAt(i) == '0') { //se o caractere for 0, percorre a árvore para esquerda
                raiz = raiz.esquerdo;
            } else if (mensagemCodificada.charAt(i) == '1') { //se o caractere for 1, percorre a árvore para direita
                raiz = raiz.direito;
            }

            assert raiz != null;
            if (raiz.esquerdo == null && raiz.direito == null) { //se o nó atual for folha, adiciona o caractere na StringBuilder
                if(raiz.caractere == '~') { //se for "~", substitui por "\n"
                    mensagemDecodificada.append('\n');
                } else {
                    mensagemDecodificada.append(raiz.caractere);
                }
                raiz = Compactador.fila.front(); //retorna para a raiz da árvore
            }
        }

        return mensagemDecodificada.toString(); //retorna mensagem decodificada
    }

    public void descompactar(BufferedReader arquivoCompactado) throws IOException {
        this.escritorArquivoDescompactado.print(decodificarTabela(arquivoCompactado));
//        this.escritorArquivoDescompactado.print(decodificarMensagem(Compactador.fila.front(), arquivoCompactado.readLine()));

        this.escritorArquivoDescompactado.close();
        System.out.println("Descompactação concluída!");
    }
}
