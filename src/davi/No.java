package davi;

public class No {
    public Character caractere;
    public No proximo;
    public No direito;
    public No esquerdo;
    public No anterior;
    public int frequencia;

    public No(char letra, int frequencia){
        this.caractere = letra;
        this.proximo = null;
        this.anterior = null;
        this.direito = null;
        this.esquerdo = null;
        this.frequencia = frequencia;
    }

    public No(No esquerdo, No direito){
        this.frequencia = esquerdo.frequencia + direito.frequencia;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

}