package huffman;

public class NoHuffman {
    public NoHuffman pai;
    public NoHuffman direita;
    public NoHuffman esquerda;
    public Character caractere;

    public NoHuffman(Character caractere, NoHuffman pai) {
        this.caractere = caractere;
        this.pai = pai;
    }
}
