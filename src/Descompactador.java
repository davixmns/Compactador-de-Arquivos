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

    public void descompactar(BufferedReader arquivoCompactado) throws IOException {
        reconstruirArvore(arquivoCompactado);
        this.escritorArquivoDescompactado.print(decodificarMensagem(arquivoCompactado));

        this.escritorArquivoDescompactado.close();
        System.out.println("Descompactação concluída!");
    }

    public void reconstruirArvore(BufferedReader arquivoCompactado) throws IOException {
        String linha = arquivoCompactado.readLine();

        this.arvoreHuffmann.setRaiz(new NoHuffman(null, null)); //cria raiz da arvore de huffman
        reconstruirArvore(this.arvoreHuffmann.getRaiz(), linha);
    }

    public void reconstruirArvore(NoHuffman raiz, String linha) { //reconstrução da árvore de huffman
        if (linha.charAt(this.posicaoLinhaArvore) == '1') { //se for 1 lê o binário seguinte
            StringBuilder sb = new StringBuilder();

            for (int i = this.posicaoLinhaArvore + 1; i < posicaoLinhaArvore + 9; i++) {
                sb.append(linha.charAt(i));
            }

            int decimal = Integer.parseInt(sb.toString(), 2); //binário -> decimal

            raiz.caractere = (char) decimal; //grava o decimal -> caractere no nó
            posicaoLinhaArvore = posicaoLinhaArvore + 8; //avança a posição da linha
            return;
        }

        if (linha.charAt(this.posicaoLinhaArvore) == '0') { //se for 0, cria os filhos
            raiz.esquerda = new NoHuffman(null, raiz); //cria nó esquerdo
            posicaoLinhaArvore++; //avança a posição da linha
            reconstruirArvore(raiz.esquerda, linha); //vai para a esquerda do nó (Recursividade)

            raiz.direita = new NoHuffman(null, raiz); //cria nó direito
            posicaoLinhaArvore++; //avança a posição da linha
            reconstruirArvore(raiz.direita, linha); //vai para a direita do nó (Recursividade)
        }
    }

    public String decodificarMensagem(BufferedReader arquivoCompactado) throws IOException {
        String linha = arquivoCompactado.readLine();
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
                mensagemDecodificada.append(raiz.caractere);
                raiz = this.arvoreHuffmann.getRaiz(); //retorna para a raiz da árvore
            }
        }

        return mensagemDecodificada.toString(); //retorna mensagem decodificada
    }
}
