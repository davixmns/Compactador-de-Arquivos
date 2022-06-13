import java.io.*;

public class Descompactador {
    private final PrintWriter escritorArquivoDescompactado;
    private final BufferedReader leitorArquivoTabela;
    private final ArvoreHuffman arvoreHuffmann;
    private int posicaoLinhaArvore;

    public Descompactador() throws IOException {
        this.arvoreHuffmann = new ArvoreHuffman();
        this.escritorArquivoDescompactado = new PrintWriter(new FileWriter("arquivos/saida/descompactado.txt"));
        this.leitorArquivoTabela = new BufferedReader(new FileReader("arquivos/auxiliar/tabela.txt"));
        this.posicaoLinhaArvore = 0;
    }

    public String decodificarMensagem(BufferedReader arquivoCompactado) throws IOException {
        String linha = arquivoCompactado.readLine() + '~';
        return decodificarMensagem(arvoreHuffmann.getRaiz(), linha);
    }

    public String decodificarMensagem(NoHuffman raiz, String mensagemCodificada) {
        StringBuilder mensagemDecodificada = new StringBuilder(); //StringBuilder para mensagem decodificada

        for (int i = 0; i < mensagemCodificada.length(); i++) {
            if (mensagemCodificada.charAt(i) == '0') { //se o caractere for 0, percorre a árvore para esquerda
                raiz = raiz.esquerda;
            } else if (mensagemCodificada.charAt(i) == '1') { //se o caractere for 1, percorre a árvore para direita
                raiz = raiz.direita;
            }

            assert raiz != null;
            if (raiz.esquerda == null && raiz.direita == null) { //se o nó atual for folha, adiciona o caractere na StringBuilder
                if (raiz.caractere == '~') { //se for "~", substitui por "\n"
                    mensagemDecodificada.append('\n');
                } else {
                    mensagemDecodificada.append(raiz.caractere);
                }
                raiz = this.arvoreHuffmann.getRaiz(); //retorna para a raiz da árvore
            }
        }

        return mensagemDecodificada.toString(); //retorna mensagem decodificada
    }

    public void reconstruirArvore(BufferedReader arquivoCompactado) throws IOException {
        String linha = arquivoCompactado.readLine();

        this.arvoreHuffmann.setRaiz(new NoHuffman(null, null));
        reconstruirArvore(this.arvoreHuffmann.getRaiz(), linha);
    }

    public void reconstruirArvore(NoHuffman raiz, String linha) {
        if (linha.charAt(this.posicaoLinhaArvore) == '1') {
            StringBuilder sb = new StringBuilder();

            for (int i = this.posicaoLinhaArvore + 1; i < posicaoLinhaArvore + 9; i++) {
                sb.append(linha.charAt(i));
            }

            int decimal = Integer.parseInt(sb.toString(), 2);

            raiz.caractere = (char) decimal;
            posicaoLinhaArvore = posicaoLinhaArvore + 8;
            return;
        }

        if (linha.charAt(this.posicaoLinhaArvore) == '0') {
            raiz.esquerda = new NoHuffman(null, raiz);
            posicaoLinhaArvore++;
            reconstruirArvore(raiz.esquerda, linha);

            raiz.direita = new NoHuffman(null, raiz);
            posicaoLinhaArvore++;
            reconstruirArvore(raiz.direita, linha);
        }
    }

    public void descompactar(BufferedReader arquivoCompactado) throws IOException {
        reconstruirArvore(arquivoCompactado);
        this.escritorArquivoDescompactado.print(decodificarMensagem(arquivoCompactado));
        this.escritorArquivoDescompactado.close();
        System.out.println("Descompactação concluída!");
    }
}
