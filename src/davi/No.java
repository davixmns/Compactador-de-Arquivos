package davi;

public class No {
    public char letra;
    public No proximo;
    public No direito;
    public No esquerdo;
    public No anterior;
    public int frequencia;

    public No(char letra, int frequencia){
        this.letra = letra;
        this.proximo = null;
        this.anterior = null;
        this.direito = null;
        this.esquerdo = null;
        this.frequencia = frequencia;
    }

    public No(int a, int b, No esquerdo, No direito){
        this.frequencia = a + b;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

}